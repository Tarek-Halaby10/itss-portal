package com.itss.auth.controller;

import com.itss.auth.dto.request.*;
import com.itss.auth.entity.User;
import com.itss.auth.mapper.UserMapper;
import com.itss.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private com.itss.auth.security.JwtUtil jwtUtil;

    @GetMapping("/test")
public ResponseEntity<?> test() {
    return ResponseEntity.ok(Map.of("message", "Auth endpoint is working!", "timestamp", System.currentTimeMillis()));
}

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationRequest request) {
        try {
            User user = userService.registerUser(request);
            return ResponseEntity.ok(userMapper.toResponse(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsernameOrEmail(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails.getUsername(), Map.of());
            String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername(), Map.of());
            return ResponseEntity.ok(Map.of(
                "accessToken", token,
                "refreshToken", refreshToken
            ));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody TokenRefreshRequest request) {
        try {
            if (!jwtUtil.validateToken(request.getRefreshToken())) {
                return ResponseEntity.status(401).body(Map.of("error", "Invalid refresh token"));
            }
            String username = jwtUtil.extractUsername(request.getRefreshToken());
            String token = jwtUtil.generateToken(username, Map.of());
            return ResponseEntity.ok(Map.of("accessToken", token));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid refresh token"));
        }
    }

    @PostMapping("/invite")
    public ResponseEntity<?> invite(@RequestBody UserInviteRequest request, @RequestParam String invitedBy) {
        try {
            userService.inviteUser(request, invitedBy);
            return ResponseEntity.ok(Map.of("message", "Invite sent"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/approve/{userId}")
    public ResponseEntity<?> approve(@PathVariable Long userId) {
        try {
            userService.approveUser(userId);
            return ResponseEntity.ok(Map.of("message", "User approved"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/decline/{userId}")
    public ResponseEntity<?> decline(@PathVariable Long userId) {
        try {
            userService.declineUser(userId);
            return ResponseEntity.ok(Map.of("message", "User declined"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/reset-password-request")
    public ResponseEntity<?> resetPasswordRequest(@RequestBody PasswordResetRequest request) {
        try {
            userService.resetPasswordRequest(request);
            return ResponseEntity.ok(Map.of("message", "Password reset email sent"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetConfirmRequest request) {
        try {
            userService.resetPassword(request);
            return ResponseEntity.ok(Map.of("message", "Password reset successful"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
} 
