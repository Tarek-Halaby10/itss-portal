package com.itss.portal.authentication.controller;

import com.itss.portal.authentication.model.Role;
import com.itss.portal.authentication.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/admin/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<Role> createRole(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Set<String> permissions,
            @RequestParam(defaultValue = "false") boolean template) {
        Role role = roleService.createRole(name, description, permissions, template);
        return ResponseEntity.ok(role);
    }

    // TODO: add update, delete, list endpoints
}
