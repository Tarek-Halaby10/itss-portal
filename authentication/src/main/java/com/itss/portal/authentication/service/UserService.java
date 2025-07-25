package com.itss.portal.authentication.service;

import com.itss.portal.authentication.dto.ProfileUpdateDto;
import com.itss.portal.authentication.dto.UserProfileDto;
import com.itss.portal.authentication.exception.EntityNotFoundException;
import com.itss.portal.authentication.model.User;
import com.itss.portal.authentication.model.Role;
import com.itss.portal.authentication.model.Position;
import com.itss.portal.authentication.repository.UserRepository;
import com.itss.portal.authentication.repository.RoleRepository;
import com.itss.portal.authentication.repository.PositionRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
  private final UserRepository userRepo;
  private final RoleRepository roleRepo;
  private final PositionRepository posRepo;
  private final PasswordEncoder encoder;
  private final AuditService audit;

  public UserService(UserRepository userRepo,
                     RoleRepository roleRepo,
                     PositionRepository posRepo,
                     AuditService audit,
                     PasswordEncoder encoder) {
    this.userRepo = userRepo;
    this.roleRepo = roleRepo;
    this.posRepo = posRepo;
    this.audit = audit;
    this.encoder  = encoder;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepo.findByUsername(username)
                 .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    return org.springframework.security.core.userdetails.User
      .withUsername(user.getUsername())
      .password(user.getPasswordHash())
      .authorities(user.getRole().getPermissions().stream()
                   .map(p -> p.getName())
                   .toArray(String[]::new))
      .accountLocked(!user.isEnabled())
      .build();
  }

  public List<User> listPending() {
    return userRepo.findAll().stream()
      .filter(u -> !u.isEnabled())
      .toList();
  }

  public void approve(Long userId, String positionName) {
    User u = userRepo.findById(userId)
              .orElseThrow(() -> new EntityNotFoundException("User"));
    Role r = roleRepo.findByName(u.getRole().getName())
              .orElseThrow(() -> new EntityNotFoundException("Role"));
    Position p = posRepo.findByName(positionName)
                 .orElseThrow(() -> new EntityNotFoundException("Position"));
    u.setEnabled(true);
    u.setPosition(p);
    userRepo.save(u);
    audit.log(u, "APPROVE_USER", "Approved with position " + positionName);
  }

  public void block(Long userId) {
    User u = userRepo.findById(userId)
              .orElseThrow(() -> new EntityNotFoundException("User"));
    u.setEnabled(false);
    userRepo.save(u);
    audit.log(u, "BLOCK_USER", null);
  }
  /** Map User → UserProfileDto */
  public UserProfileDto getProfile(String username) {
      User u = userRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User"));
      return new UserProfileDto(
          u.getId(),
          u.getUsername(),
          u.getEmail(),
          u.getRole().getName(),
          u.getPosition() != null ? u.getPosition().getName() : null
      );
  }

  /** Update email & username */
  public void updateProfile(String username, ProfileUpdateDto dto) {
      User u = userRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User"));
      u.setEmail(dto.getEmail());
      u.setUsername(dto.getUsername());
      userRepo.save(u);
  }

  /** Verify old password & set new */
  public void changePassword(String username, String oldPwd, String newPwd) {
      User u = userRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User"));
      if (!encoder.matches(oldPwd, u.getPasswordHash())) {
          throw new IllegalArgumentException("Old password is incorrect");
      }
      u.setPasswordHash(encoder.encode(newPwd));
      userRepo.save(u);
  }
  public Page<UserProfileDto> listAllUsers(Pageable pageable) {
	    return userRepo.findAll(pageable)
	        .map(u -> new UserProfileDto(
	            u.getId(),
	            u.getUsername(),
	            u.getEmail(),
	            u.getRole().getName(),
	            u.getPosition() != null ? u.getPosition().getName() : null
	        ));
	}

	public UserProfileDto getUserById(Long id) {
	    User u = userRepo.findById(id)
	              .orElseThrow(() -> new EntityNotFoundException("User"));
	    return new UserProfileDto(
	        u.getId(), u.getUsername(), u.getEmail(),
	        u.getRole().getName(),
	        u.getPosition() != null ? u.getPosition().getName() : null
	    );
	}
}
