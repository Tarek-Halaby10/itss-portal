package com.itss.auth.controller;

import com.itss.auth.dto.request.*;
import com.itss.auth.entity.RefreshToken;
import com.itss.auth.entity.User;
import com.itss.auth.mapper.UserMapper;
import com.itss.auth.repository.UserRepository;
import com.itss.auth.service.RefreshTokenService;
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
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private com.itss.auth.security.JwtUtil jwtUtil;
    @Autowired
    private RefreshTokenService refreshTokenService;

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
            
            // Find the user entity
            User user = userRepository.findByUsernameWithRolesAndPermissions(userDetails.getUsername())
                    .or(() -> userRepository.findByEmailWithRolesAndPermissions(userDetails.getUsername()))
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            // Generate tokens
            String accessToken = jwtUtil.generateToken(userDetails.getUsername(), Map.of());
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
            
            return ResponseEntity.ok(Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken.getToken(),
                "message", "Login successful"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody(required = false) TokenRefreshRequest request) {
        try {
            // Get current authenticated user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            
            if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getName())) {
                return ResponseEntity.status(401).body(Map.of("error", "User is not signed in"));
            }
            
            String username = authentication.getName();
            
            // Find user and check if they have an active refresh token
            Optional<User> userOpt = userRepository.findByUsernameWithRolesAndPermissions(username);
            if (userOpt.isEmpty()) {
                return ResponseEntity.status(401).body(Map.of("error", "User is not signed in"));
            }
            
            User user = userOpt.get();
            
            // If refresh token is provided in request body, validate it belongs to this user
            if (request != null && request.getRefreshToken() != null) {
                Optional<RefreshToken> refreshTokenOpt = refreshTokenService.findByToken(request.getRefreshToken());
                if (refreshTokenOpt.isEmpty() || !refreshTokenOpt.get().getUser().getId().equals(user.getId())) {
                    return ResponseEntity.status(401).body(Map.of("error", "Invalid session or user is not signed in"));
                }
            }
            
            // Revoke all refresh tokens for this user
            refreshTokenService.revokeTokensByUser(user);
            
            // Clear the security context
            SecurityContextHolder.clearContext();
            
            return ResponseEntity.ok(Map.of("message", "Logout successful"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody TokenRefreshRequest request) {
        try {
            String requestRefreshToken = request.getRefreshToken();
            
            // Find refresh token in database
            Optional<RefreshToken> refreshTokenOpt = refreshTokenService.findByToken(requestRefreshToken);
            if (refreshTokenOpt.isEmpty()) {
                return ResponseEntity.status(401).body(Map.of("error", "Invalid refresh token"));
            }
            
            // Verify token is valid (not expired or revoked)
            RefreshToken refreshToken = refreshTokenService.verifyExpiration(refreshTokenOpt.get());
            
            // Generate new access token
            String newAccessToken = jwtUtil.generateToken(refreshToken.getUser().getUsername(), Map.of());
            
            return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid refresh token"));
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
