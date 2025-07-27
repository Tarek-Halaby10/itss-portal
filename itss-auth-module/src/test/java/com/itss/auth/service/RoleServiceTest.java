package com.itss.auth.service;

import com.itss.auth.dto.request.RoleCreateRequest;
import com.itss.auth.entity.Role;
import com.itss.auth.repository.RoleRepository;
import com.itss.auth.repository.PermissionRepository;
import com.itss.auth.mapper.RoleMapper;
import com.itss.auth.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class RoleServiceTest {
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PermissionRepository permissionRepository;
    @Mock
    private RoleMapper roleMapper;
    @Mock
    private AuditLogService auditLogService;
    @InjectMocks
    private RoleServiceImpl roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRoleThrowsIfNameExists() {
        RoleCreateRequest req = new RoleCreateRequest();
        req.setName("ADMIN");
        when(roleRepository.existsByName("ADMIN")).thenReturn(true);
        
        Exception ex = assertThrows(IllegalArgumentException.class, () -> roleService.createRole(req));
        assertEquals("Role name already exists", ex.getMessage());
    }

    @Test
    void testCreateRoleSuccess() {
        RoleCreateRequest req = new RoleCreateRequest();
        req.setName("ADMIN");
        req.setDescription("Admin role");
        
        Role role = new Role();
        role.setName("ADMIN");
        
        when(roleRepository.existsByName("ADMIN")).thenReturn(false);
        when(roleMapper.toEntity(req)).thenReturn(role);
        when(roleRepository.save(any(Role.class))).thenReturn(role);
        
        Role result = roleService.createRole(req);
        
        assertNotNull(result);
        assertEquals("ADMIN", result.getName());
        verify(auditLogService).logAction(any(), eq("CREATE_ROLE"), any(), any());
    }

    @Test
    void testDeleteRole() {
        Long roleId = 1L;
        
        roleService.deleteRole(roleId);
        
        verify(roleRepository).deleteById(roleId);
        verify(auditLogService).logAction(eq(roleId), eq("DELETE_ROLE"), any(), any());
    }
} 