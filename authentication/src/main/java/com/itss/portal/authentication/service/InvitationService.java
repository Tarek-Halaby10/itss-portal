package com.itss.portal.authentication.service;

import com.itss.portal.authentication.exception.EntityNotFoundException;
import com.itss.portal.authentication.model.Invitation;
import com.itss.portal.authentication.model.Role;
import com.itss.portal.authentication.model.User;
import com.itss.portal.authentication.repository.InvitationRepository;
import com.itss.portal.authentication.repository.RoleRepository;
import com.itss.portal.authentication.repository.UserRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.UUID;

@Service
public class InvitationService {
  private final InvitationRepository invRepo;
  private final RoleRepository roleRepo;
  private final UserRepository userRepo;
  private final AuditService audit;
  private final EmailService emailService;
  @Value("${app.base-url}") 
  private String baseUrl;
  
  public InvitationService(InvitationRepository invRepo,
                           RoleRepository roleRepo,
                           UserRepository userRepo,
                           AuditService audit,
                           EmailService emailService) {
    this.invRepo = invRepo;
    this.roleRepo = roleRepo;
    this.userRepo = userRepo;
    this.audit = audit;
    this.emailService = emailService;
  }

  public void invite(String email, String roleName) {
	    Role role = roleRepo.findByName(roleName)
	                .orElseThrow(() -> new EntityNotFoundException("Role"));

	    Invitation inv = new Invitation();
	    inv.setEmail(email);
	    inv.setRole(role);
	    inv.setToken(UUID.randomUUID().toString());
	    inv.setExpiryDate(Instant.now().plusSeconds(86_400)); // 24h
	    invRepo.save(inv);

	    // ← Here’s where you add it:
	    String link = String.format("%s/api/invite/accept?token=%s", baseUrl, inv.getToken());
	    String body = "<p>You’ve been invited to ITSS Portal.</p>"
	                + "<p><a href=\"" + link + "\">Click here to accept your invitation</a></p>";
	    emailService.sendHtmlEmail(inv.getEmail(), "ITSS Portal Invitation", body);
	  }

  public void accept(String token) {
    Invitation inv = invRepo.findByToken(token)
                   .orElseThrow(() -> new EntityNotFoundException("Invitation"));
    if (inv.getExpiryDate().isBefore(Instant.now())) {
      throw new IllegalStateException("Invitation expired");
    }
    User u = new User();
    u.setEmail(inv.getEmail());
    u.setRole(inv.getRole());
    // password to be set on signup
    userRepo.save(u);
    inv.setAccepted(true);
    invRepo.save(inv);
    audit.log(u, "ACCEPT_INVITE", "Invited via token");
  }
}
