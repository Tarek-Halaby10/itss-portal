package com.itss.auth.service.impl;

import com.itss.auth.dto.request.UserInviteRequest;
import com.itss.auth.entity.Invite;
import com.itss.auth.entity.Role;
import com.itss.auth.entity.User;
import com.itss.auth.repository.InviteRepository;
import com.itss.auth.repository.RoleRepository;
import com.itss.auth.repository.UserRepository;
import com.itss.auth.service.InviteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import com.itss.auth.service.EmailService;

@Service
public class InviteServiceImpl implements InviteService {
    @Autowired
    private InviteRepository inviteRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;

    @Override
    @Transactional
    public void createInvite(UserInviteRequest request, String invitedByUsername) {
        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));
        User invitedBy = userRepository.findByUsername(invitedByUsername)
                .orElseThrow(() -> new IllegalArgumentException("Inviter not found"));
        String token = UUID.randomUUID().toString();
        Invite invite = Invite.builder()
                .email(request.getEmail())
                .role(role)
                .token(token)
                .status(Invite.Status.PENDING)
                .invitedBy(invitedBy)
                .expiresAt(Instant.now().plusSeconds(72 * 3600)) // 72 hours
                .build();
        inviteRepository.save(invite);
        // Send invite email with token
        emailService.sendInviteEmail(request.getEmail(), token, role.getName());
    }

    @Override
    public Optional<Invite> getInviteByToken(String token) {
        return inviteRepository.findByToken(token);
    }

    @Override
    @Transactional
    public void acceptInvite(String token, String password) {
        Invite invite = inviteRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid invite token"));
        if (invite.getStatus() != Invite.Status.PENDING || invite.getExpiresAt().isBefore(Instant.now())) {
            throw new IllegalStateException("Invite expired or already used");
        }
        // Create user with approved status
        User user = User.builder()
                .username(invite.getEmail())
                .email(invite.getEmail())
                .password(password) // Should be encoded by service using this method
                .status(User.Status.APPROVED)
                .roles(java.util.Set.of(invite.getRole()))
                .build();
        userRepository.save(user);
        invite.setStatus(Invite.Status.ACCEPTED);
        inviteRepository.save(invite);
    }

    @Override
    @Transactional
    public void declineInvite(String token) {
        Invite invite = inviteRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid invite token"));
        invite.setStatus(Invite.Status.DECLINED);
        inviteRepository.save(invite);
    }
} 
