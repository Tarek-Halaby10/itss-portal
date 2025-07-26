// RoleMapper.java
package com.itss.auth.mapper;

import com.itss.auth.dto.response.RoleResponse;
import com.itss.auth.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleMapper {

    @Autowired
    private PermissionMapper permissionMapper;

    public RoleResponse toResponse(Role role) {
        if (role == null) {
            return null;
        }

        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .isTemplate(role.getIsTemplate())
                .isSystemRole(role.getIsSystemRole())
                .createdAt(role.getCreatedAt())
                .updatedAt(role.getUpdatedAt())
                .permissions(role.getPermissions() != null ?
                    permissionMapper.toResponseList(role.getPermissions().stream().toList()) :
                    new HashSet<>())
                .userCount(role.getUsers() != null ? Long.valueOf(role.getUsers().size()) : 0)
                .build();
    }

    public RoleResponse toBasicResponse(Role role) {
        if (role == null) {
            return null;
        }

        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .isTemplate(role.getIsTemplate())
                .isSystemRole(role.getIsSystemRole())
                .build();
    }

    public Set<RoleResponse> toResponseList(List<Role> roles) {
        if (roles == null) {
            return Set.of();
        }
        return roles.stream()
                .map(this::toResponse)
                .collect(Collectors.toSet());
    }

    public List<RoleResponse> toBasicResponseList(List<Role> roles) {
        if (roles == null) {
            return List.of();
        }
        return roles.stream()
                .map(this::toBasicResponse)
                .collect(Collectors.toList());
    }
}