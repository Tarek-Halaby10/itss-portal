package com.itss.auth.service;

import com.itss.auth.entity.Permission;
import java.util.List;

public interface PermissionService {
    List<Permission> getAllPermissions();
    Permission getPermissionById(Long id);
    Permission updatePermission(Long id, String description);
} 
