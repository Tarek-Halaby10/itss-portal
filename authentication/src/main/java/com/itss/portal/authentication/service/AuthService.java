package com.itss.portal.authentication.service;

import com.itss.portal.authentication.dto.*;
import com.itss.portal.authentication.exception.EntityNotFoundException;
import com.itss.portal.authentication.exception.UserNotApprovedException;
import com.itss.portal.authentication.model.User;
import com.itss.portal.authentication.model.Invitation;
import com.itss.portal.authentication.model.PasswordResetToken;
import com.itss.portal.authentication.model.RefreshToken;
import com.itss.portal.authentication.repository.InvitationRepository;
import com.itss.portal.authentication.repository.PasswordResetTokenRepository;
import com.itss.portal.authentication.repository.UserRepository;
import com.itss.portal.authentication.security.JwtTokenProvider;
import java.time.Instant;
import java.util.UUID;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
  private final UserRepository userRepo;
  private final PasswordEncoder encoder;
  private final AuthenticationManager authManager;
  private final JwtTokenProvider jwtProvider;
  private final RefreshTokenService rtService;
  private final AuditService audit;
  private final EmailService emailService;
  private final Counter signupCounter;
  private final Counter loginSuccessCounter;
  private final Counter loginFailureCounter;
  private final Counter refreshCounter;
  private final PasswordResetTokenRepository prtRepo;

  @Value("${app.base-url}") 
  private String baseUrl;
  
  public AuthService(UserRepository userRepo,
                     PasswordEncoder encoder,
                     AuthenticationManager authManager,
                     JwtTokenProvider jwtProvider,
                     RefreshTokenService rtService,
                     AuditService audit,
                     EmailService emailService,
                     MeterRegistry registry,
                     PasswordResetTokenRepository prtRepo) {
    this.userRepo = userRepo;
    this.encoder = encoder;
    this.authManager = authManager;
    this.jwtProvider = jwtProvider;
    this.rtService = rtService;
    this.audit = audit;
    this.prtRepo = prtRepo;
    this.emailService = emailService;
    this.signupCounter = Counter.builder("auth.signup.attempts")
            .description("Number of signup attempts")
            .register(registry);

this.loginSuccessCounter = Counter.builder("auth.login.success")
                  .description("Number of successful logins")
                  .register(registry);

this.loginFailureCounter = Counter.builder("auth.login.failure")
                  .description("Number of failed logins")
                  .register(registry);

this.refreshCounter = Counter.builder("auth.token.refresh")
             .description("Number of token refreshes")
             .register(registry);
  }

  public void signup(SignupRequest req) {
	    signupCounter.increment();

    var user = new User();
    user.setUsername(req.getUsername());
    user.setEmail(req.getEmail());
    user.setPasswordHash(encoder.encode(req.getPassword()));
    // default role/position to be set later by admin
    userRepo.save(user);
    audit.log(user, "SIGNUP", "Self-registered, pending approval");
  }

  public JwtResponse login(LoginRequest req) {
    Authentication auth = authManager.authenticate(
      new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
    User user = userRepo.findByUsername(req.getUsername())
                .orElseThrow();
    if (!user.isEnabled()) {
        loginFailureCounter.increment();
      throw new UserNotApprovedException("Account not yet approved");
    }
    String access = jwtProvider.generateAccessToken(user.getUsername());
    RefreshToken rt = rtService.createRefreshToken(user);
    audit.log(user, "LOGIN", null);
    loginSuccessCounter.increment();
    return new JwtResponse(access, rt.getToken());
  }

  public JwtResponse refresh(TokenRefreshRequest req) {
	    // 1) verify and fetch old
	    RefreshToken oldRt = rtService.verifyExpiration(req.getRefreshToken());
	    // 2) delete it
	    refreshCounter.increment();

	    rtService.deleteByToken(oldRt.getToken());
	    // 3) create a fresh one
	    RefreshToken newRt = rtService.createRefreshToken(oldRt.getUser());
	    // 4) log and return
	    audit.log(oldRt.getUser(), "REFRESH_TOKEN_ROTATED", null);
	    String access = jwtProvider.generateAccessToken(oldRt.getUser().getUsername());
	    return new JwtResponse(access, newRt.getToken());
	}
  
  public void logout(String refreshToken) {
	    rtService.deleteByToken(refreshToken);
	}
  
  public void requestPasswordReset(String email) {
	    User user = userRepo.findByEmail(email)
	        .orElseThrow(() -> new EntityNotFoundException("User"));
	    
	    // Invalidate any existing tokens
	    prtRepo.deleteByUserId(user.getId());

	    // Create a new reset token
	    PasswordResetToken prt = new PasswordResetToken();
	    prt.setUser(user);
	    prt.setToken(UUID.randomUUID().toString());
	    prt.setExpiryDate(Instant.now().plusSeconds(3600)); // 1h
	    prtRepo.save(prt);

	    // Send email
	    String link = String.format("%s/api/auth/reset-password/confirm?token=%s",
	                                baseUrl, prt.getToken());
	    String body = "<p>To reset your password, click:</p>"
	                + "<p><a href=\"" + link + "\">Reset Password</a></p>";
	    emailService.sendHtmlEmail(user.getEmail(),
	                               "ITSS Portal Password Reset",
	                               body);

	    audit.log(user, "REQUEST_RESET", "Token=" + prt.getToken());
	}


  /**
   * Completes reset: verifies token and updates the user’s password.
   */
  public void confirmPasswordReset(String token, String newPassword) {
	    PasswordResetToken prt = prtRepo.findByToken(token)
	        .orElseThrow(() -> new EntityNotFoundException("Reset token"));

	    if (prt.isUsed() || prt.getExpiryDate().isBefore(Instant.now())) {
	        throw new IllegalStateException("Reset token invalid or expired");
	    }

	    User user = prt.getUser();
	    user.setPasswordHash(encoder.encode(newPassword));
	    userRepo.save(user);

	    prt.setUsed(true);
	    prtRepo.save(prt);

	    audit.log(user, "CONFIRM_RESET", null);
	}


  // resetPasswordRequest and confirmResetPassword omitted for brevity
}
