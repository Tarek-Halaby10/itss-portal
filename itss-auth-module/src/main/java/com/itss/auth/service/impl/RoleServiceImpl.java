package com.itss.auth.service.impl;

import com.itss.auth.dto.request.RoleCreateRequest;
import com.itss.auth.dto.request.RoleUpdateRequest;
import com.itss.auth.entity.Role;
import com.itss.auth.entity.Permission;
import com.itss.auth.repository.RoleRepository;
import com.itss.auth.repository.PermissionRepository;
import com.itss.auth.service.RoleService;
import com.itss.auth.service.AuditLogService;
import com.itss.auth.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private AuditLogService auditLogService;

    @Override
    @Transactional
    public Role createRole(RoleCreateRequest request) {
        if (roleRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Role name already exists");
        }
        Role role = roleMapper.toEntity(request);
        if (request.getPermissionIds() != null) {
            Set<Permission> permissions = request.getPermissionIds().stream()
                    .map(pid -> permissionRepository.findById(pid).orElseThrow(() -> new IllegalArgumentException("Permission not found: " + pid)))
                    .collect(Collectors.toSet());
            role.setPermissions(permissions);
        }
        Role saved = roleRepository.save(role);
        auditLogService.logAction(saved.getId(), "CREATE_ROLE", "/api/roles", "Role created: " + saved.getName());
        return saved;
    }

    @Override
    @Transactional
    public Role updateRole(Long id, RoleUpdateRequest request) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Role not found"));
        if (request.getName() != null) role.setName(request.getName());
        if (request.getDescription() != null) role.setDescription(request.getDescription());
        if (request.getPermissionIds() != null) {
            Set<Permission> permissions = request.getPermissionIds().stream()
                    .map(pid -> permissionRepository.findById(pid).orElseThrow(() -> new IllegalArgumentException("Permission not found: " + pid)))
                    .collect(Collectors.toSet());
            role.setPermissions(permissions);
        }
        Role saved = roleRepository.save(role);
        auditLogService.logAction(id, "UPDATE_ROLE", "/api/roles/" + id, "Role updated: " + saved.getName());
        return saved;
    }

    @Override
    @Transactional
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
        auditLogService.logAction(id, "DELETE_ROLE", "/api/roles/" + id, "Role deleted");
    }

    @Override
    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Role not found"));
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional
    public void assignPermissions(Long roleId, List<Long> permissionIds) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new IllegalArgumentException("Role not found"));
        Set<Permission> permissions = permissionIds.stream()
                .map(pid -> permissionRepository.findById(pid).orElseThrow(() -> new IllegalArgumentException("Permission not found: " + pid)))
                .collect(Collectors.toSet());
        role.setPermissions(permissions);
        roleRepository.save(role);
        auditLogService.logAction(roleId, "ASSIGN_PERMISSIONS", "/api/roles/" + roleId + "/permissions", "Permissions assigned: " + permissionIds);
    }
} 
