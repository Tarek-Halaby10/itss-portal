package com.itss.auth.service.impl;

import com.itss.auth.entity.Permission;
import com.itss.auth.repository.PermissionRepository;
import com.itss.auth.service.PermissionService;
import com.itss.auth.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private AuditLogService auditLogService;

    @Override
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    @Override
    public Permission getPermissionById(Long id) {
        return permissionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Permission not found"));
    }

    @Override
    @Transactional
    public Permission updatePermission(Long id, String description) {
        Permission permission = permissionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Permission not found"));
        permission.setDescription(description);
        Permission saved = permissionRepository.save(permission);
        auditLogService.logAction(id, "UPDATE_PERMISSION", "/api/permissions/" + id, "Permission updated: " + description);
        return saved;
    }
} 
