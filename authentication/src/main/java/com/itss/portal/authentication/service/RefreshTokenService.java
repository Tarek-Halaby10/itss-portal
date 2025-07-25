package com.itss.portal.authentication.service;

import com.itss.portal.authentication.exception.TokenRefreshException;
import com.itss.portal.authentication.model.RefreshToken;
import com.itss.portal.authentication.model.User;
import com.itss.portal.authentication.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {
  @Value("${security.jwt.refresh-token-validity-ms}")
  private long refreshTokenDurationMs;

  private final RefreshTokenRepository rtRepo;
  private final AuditService audit;

  public RefreshTokenService(RefreshTokenRepository rtRepo, AuditService audit) {
    this.rtRepo = rtRepo;
    this.audit = audit;
  }

  public RefreshToken createRefreshToken(User user) {
    rtRepo.deleteByUser(user);
    RefreshToken rt = new RefreshToken();
    rt.setUser(user);
    rt.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
    rt.setToken(UUID.randomUUID().toString());
    rtRepo.save(rt);
    audit.log(user, "CREATE_REFRESH_TOKEN", null);
    return rt;
  }

  public RefreshToken verifyExpiration(String token) {
    RefreshToken rt = rtRepo.findByToken(token)
                     .orElseThrow(() -> new TokenRefreshException(token, "Not found"));
    if (rt.getExpiryDate().isBefore(Instant.now())) {
      rtRepo.delete(rt);
      throw new TokenRefreshException(token, "Expired");
    }
    return rt;
  }
  /** Delete a refresh token outright (for logout). */
  public void deleteByToken(String token) {
      rtRepo.findByToken(token).ifPresent(rtRepo::delete);
  }
}
