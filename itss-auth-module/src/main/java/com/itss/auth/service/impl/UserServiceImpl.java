package com.itss.auth.service.impl;

import com.itss.auth.dto.request.UserRegistrationRequest;
import com.itss.auth.entity.User;
import com.itss.auth.entity.Role;
import com.itss.auth.repository.UserRepository;
import com.itss.auth.repository.RoleRepository;
import com.itss.auth.service.UserService;
import com.itss.auth.service.AuditLogService;
import com.itss.auth.service.InviteService;
import com.itss.auth.service.PasswordResetService;
import com.itss.auth.dto.request.UserInviteRequest;
import com.itss.auth.dto.request.PasswordResetRequest;
import com.itss.auth.dto.request.PasswordResetConfirmRequest;
import com.itss.auth.mapper.UserMapper;
import com.itss.auth.exception.CustomExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuditLogService auditLogService;
    @Autowired
    private InviteService inviteService;
    @Autowired
    private PasswordResetService passwordResetService;

    @Override
    @Transactional
    public User registerUser(UserRegistrationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new CustomExceptions.UsernameAlreadyExistsException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomExceptions.EmailAlreadyExistsException("Email already exists");
        }
        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus(User.Status.PENDING);
        // Assign default role (e.g., USER)
        Optional<Role> defaultRole = roleRepository.findByName("USER");
        defaultRole.ifPresent(role -> user.setRoles(Set.of(role)));
        User saved = userRepository.save(user);
        auditLogService.logAction(saved.getId(), "REGISTER_USER", "/api/auth/register", "User registered and pending approval");
        return saved;
    }

    @Override
    @Transactional
    public void approveUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomExceptions.UserNotFoundException("User not found"));
        user.setStatus(User.Status.APPROVED);
        userRepository.save(user);
        auditLogService.logAction(userId, "APPROVE_USER", "/api/auth/approve/" + userId, "User approved");
    }

    @Override
    @Transactional
    public void declineUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomExceptions.UserNotFoundException("User not found"));
        user.setStatus(User.Status.DECLINED);
        userRepository.save(user);
        auditLogService.logAction(userId, "DECLINE_USER", "/api/auth/decline/" + userId, "User declined");
    }

    @Override
    @Transactional
    public void blockUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomExceptions.UserNotFoundException("User not found"));
        user.setStatus(User.Status.BLOCKED);
        userRepository.save(user);
        auditLogService.logAction(userId, "BLOCK_USER", "/api/users/block/" + userId, "User blocked");
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getPendingUsers() {
        return userRepository.findAll().stream()
                .filter(u -> u.getStatus() == User.Status.PENDING)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getBlockedUsers() {
        return userRepository.findAll().stream()
                .filter(u -> u.getStatus() == User.Status.BLOCKED)
                .collect(Collectors.toList());
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new CustomExceptions.UserNotFoundException("User not found"));
    }

    @Override
    public void inviteUser(UserInviteRequest request, String invitedByUsername) {
        inviteService.createInvite(request, invitedByUsername);
        // For audit, we need the inviter's userId. Let's fetch it:
        User inviter = userRepository.findByUsername(invitedByUsername).orElse(null);
        Long inviterId = inviter != null ? inviter.getId() : null;
        auditLogService.logAction(inviterId, "INVITE_USER", "/api/auth/invite", "Invited user: " + request.getEmail() + " with roleId: " + request.getRoleId());
    }

    @Override
    public void resetPasswordRequest(PasswordResetRequest request) {
        passwordResetService.requestPasswordReset(request);
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);
        Long userId = user != null ? user.getId() : null;
        auditLogService.logAction(userId, "RESET_PASSWORD_REQUEST", "/api/auth/reset-password-request", "Password reset requested for: " + request.getEmail());
    }

    @Override
    public void resetPassword(PasswordResetConfirmRequest request) {
        passwordResetService.confirmPasswordReset(request);
        // For audit, try to get user by token (if possible)
        // This is a placeholder; actual implementation may differ
        auditLogService.logAction(null, "RESET_PASSWORD_CONFIRM", "/api/auth/reset-password", "Password reset confirmed for token: " + request.getToken());
    }
} 
