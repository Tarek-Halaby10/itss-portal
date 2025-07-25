package com.itss.portal.authentication.controller;

import com.itss.portal.authentication.dto.UserProfileDto;
import com.itss.portal.authentication.model.User;
import com.itss.portal.authentication.service.UserService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/pending")
    public ResponseEntity<List<User>> listPending() {
        return ResponseEntity.ok(userService.listPending());
    }

    @PostMapping("/approve/{userId}")
    public ResponseEntity<?> approve(
            @PathVariable Long userId,
            @RequestParam String positionName) {
        userService.approve(userId, positionName);
        return ResponseEntity.ok("User approved.");
    }

    @PostMapping("/block/{userId}")
    public ResponseEntity<?> block(@PathVariable Long userId) {
        userService.block(userId);
        return ResponseEntity.ok("User blocked.");
    }
    /** List all users (paged) */
    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_USERS')")
    public Page<UserProfileDto> listUsers(Pageable pageable) {
        return userService.listAllUsers(pageable);
    }

    /** Get one user by id */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('VIEW_USERS')")
    public UserProfileDto getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }
}
