package com.itss.portal.authentication.controller;

import com.itss.portal.authentication.dto.*;
import com.itss.portal.authentication.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

@Validated
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest request) {
        authService.signup(request);
        return ResponseEntity.ok("Signup successful. Awaiting approval.");
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
        JwtResponse tokens = authService.login(request);
        return ResponseEntity.ok(tokens);
    }
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@Valid @RequestBody TokenRefreshRequest req) {
        authService.logout(req.getRefreshToken());
        return ResponseEntity.ok("Logged out successfully.");
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(@Valid @RequestBody TokenRefreshRequest request) {
        JwtResponse tokens = authService.refresh(request);
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/reset-password/request")
    public ResponseEntity<?> requestReset(@RequestParam String email) {
        authService.requestPasswordReset(email);
        return ResponseEntity.ok("Password reset link sent.");
    }

    @PostMapping("/reset-password/confirm")
    public ResponseEntity<?> confirmReset(@Valid @RequestBody ResetPasswordRequest request) {
        authService.confirmPasswordReset(request.getToken(), request.getNewPassword());
        return ResponseEntity.ok("Password has been reset.");
    }
}
