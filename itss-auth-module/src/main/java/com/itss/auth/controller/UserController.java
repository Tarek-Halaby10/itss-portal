package com.itss.auth.controller;

import com.itss.auth.service.UserService;
import com.itss.auth.entity.User;
import com.itss.auth.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping
    @PreAuthorize("hasAuthority(T(com.itss.auth.security.PermissionConstants).VIEW_USERS)")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return ResponseEntity.ok(users.stream().map(userMapper::toResponse).toList());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/pending")
    @PreAuthorize("hasAuthority(T(com.itss.auth.security.PermissionConstants).VIEW_USERS)")
    public ResponseEntity<?> getPendingUsers() {
        try {
            List<User> users = userService.getPendingUsers();
            return ResponseEntity.ok(users.stream().map(userMapper::toResponse).toList());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/blocked")
    @PreAuthorize("hasAuthority(T(com.itss.auth.security.PermissionConstants).VIEW_USERS)")
    public ResponseEntity<?> getBlockedUsers() {
        try {
            List<User> users = userService.getBlockedUsers();
            return ResponseEntity.ok(users.stream().map(userMapper::toResponse).toList());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.itss.auth.security.PermissionConstants).VIEW_USERS)")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            return ResponseEntity.ok(userMapper.toResponse(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/block/{id}")
    @PreAuthorize("hasAuthority(T(com.itss.auth.security.PermissionConstants).BLOCK_USER)")
    public ResponseEntity<?> blockUser(@PathVariable Long id) {
        try {
            userService.blockUser(id);
            return ResponseEntity.ok(Map.of("message", "User blocked"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
} 
