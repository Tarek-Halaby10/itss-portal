package com.itss.auth.service;

import com.itss.auth.dto.request.*;
import com.itss.auth.entity.Role;
import java.util.List;

public interface RoleService {
    Role createRole(RoleCreateRequest request);
    Role updateRole(Long id, RoleUpdateRequest request);
    void deleteRole(Long id);
    Role getRoleById(Long id);
    List<Role> getAllRoles();
    void assignPermissions(Long roleId, List<Long> permissionIds);
} 
