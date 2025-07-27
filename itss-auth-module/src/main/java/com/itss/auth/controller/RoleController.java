package com.itss.auth.controller;

import com.itss.auth.dto.request.RoleCreateRequest;
import com.itss.auth.dto.request.RoleUpdateRequest;
import com.itss.auth.entity.Role;
import com.itss.auth.mapper.RoleMapper;
import com.itss.auth.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleMapper roleMapper;

    @PostMapping
    @PreAuthorize("hasAuthority(T(com.itss.auth.security.PermissionConstants).CREATE_ROLE)")
    public ResponseEntity<?> createRole(@RequestBody RoleCreateRequest request) {
        try {
            Role role = roleService.createRole(request);
            return ResponseEntity.ok(roleMapper.toResponse(role));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.itss.auth.security.PermissionConstants).UPDATE_ROLE)")
    public ResponseEntity<?> updateRole(@PathVariable Long id, @RequestBody RoleUpdateRequest request) {
        try {
            Role role = roleService.updateRole(id, request);
            return ResponseEntity.ok(roleMapper.toResponse(role));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.itss.auth.security.PermissionConstants).DELETE_ROLE)")
    public ResponseEntity<?> deleteRole(@PathVariable Long id) {
        try {
            roleService.deleteRole(id);
            return ResponseEntity.ok(Map.of("message", "Role deleted"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    @PreAuthorize("hasAuthority(T(com.itss.auth.security.PermissionConstants).VIEW_ROLES)")
    public ResponseEntity<?> getAllRoles() {
        try {
            List<Role> roles = roleService.getAllRoles();
            return ResponseEntity.ok(roles.stream().map(roleMapper::toResponse).toList());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.itss.auth.security.PermissionConstants).VIEW_ROLES)")
    public ResponseEntity<?> getRoleById(@PathVariable Long id) {
        try {
            Role role = roleService.getRoleById(id);
            return ResponseEntity.ok(roleMapper.toResponse(role));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/{id}/permissions")
    @PreAuthorize("hasAuthority(T(com.itss.auth.security.PermissionConstants).ASSIGN_PERMISSIONS)")
    public ResponseEntity<?> assignPermissions(@PathVariable Long id, @RequestBody List<Long> permissionIds) {
        try {
            roleService.assignPermissions(id, permissionIds);
            return ResponseEntity.ok(Map.of("message", "Permissions assigned"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
} 
