package com.itss.portal.authentication.controller;

import com.itss.portal.authentication.dto.*;
import com.itss.portal.authentication.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/me")
public class AccountController {

    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    /** 1. Get current user profile */
    @GetMapping
    public UserProfileDto getProfile(Authentication auth) {
        String username = auth.getName();
        return userService.getProfile(username);
    }

    /** 2. Update email/username */
    @PutMapping
    public ResponseEntity<Void> updateProfile(
            Authentication auth,
            @Valid @RequestBody ProfileUpdateDto dto) {
        userService.updateProfile(auth.getName(), dto);
        return ResponseEntity.ok().build();
    }

    /** 3. Change password */
    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(
            Authentication auth,
            @Valid @RequestBody ChangePasswordDto dto) {
        userService.changePassword(
            auth.getName(), dto.getOldPassword(), dto.getNewPassword());
        return ResponseEntity.ok().build();
    }
}
