package com.itss.portal.authentication.controller;

import com.itss.portal.authentication.model.Permission;
import com.itss.portal.authentication.repository.PermissionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/permissions")
public class PermissionController {

    private final PermissionRepository permissionRepo;

    public PermissionController(PermissionRepository permissionRepo) {
        this.permissionRepo = permissionRepo;
    }

    @GetMapping
    public ResponseEntity<List<Permission>> listAll() {
        return ResponseEntity.ok(permissionRepo.findAll());
    }
}
