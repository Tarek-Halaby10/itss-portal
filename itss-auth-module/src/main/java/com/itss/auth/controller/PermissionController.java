package com.itss.auth.controller;

import com.itss.auth.entity.Permission;
import com.itss.auth.mapper.PermissionMapper;
import com.itss.auth.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @Autowired
    private PermissionMapper permissionMapper;

    @GetMapping
    @PreAuthorize("hasAuthority(T(com.itss.auth.security.PermissionConstants).VIEW_PERMISSIONS)")
    public ResponseEntity<?> getAllPermissions() {
        try {
            List<Permission> permissions = permissionService.getAllPermissions();
            return ResponseEntity.ok(permissions.stream().map(permissionMapper::toResponse).toList());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.itss.auth.security.PermissionConstants).VIEW_PERMISSIONS)")
    public ResponseEntity<?> getPermissionById(@PathVariable Long id) {
        try {
            Permission permission = permissionService.getPermissionById(id);
            return ResponseEntity.ok(permissionMapper.toResponse(permission));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
} 