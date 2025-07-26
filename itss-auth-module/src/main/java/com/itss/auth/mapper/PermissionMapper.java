// PermissionMapper.java
package com.itss.auth.mapper;

import com.itss.auth.dto.response.PermissionResponse;
import com.itss.auth.entity.Permission;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PermissionMapper {

    public PermissionResponse toResponse(Permission permission) {
        if (permission == null) {
            return null;
        }

        return PermissionResponse.builder()
                .id(permission.getId())
                .name(permission.getName())
                .description(permission.getDescription())
                .resource(permission.getResource())
                .action(permission.getAction())
                .endpoint(permission.getEndpoint())
                .createdAt(permission.getCreatedAt())
                .build();
    }

    public Set<PermissionResponse> toResponseList(List<Permission> permissions) {
        if (permissions == null) {
            return Set.of();
        }
        return permissions.stream()
                .map(this::toResponse)
                .collect(Collectors.toSet());
    }

    public PermissionResponse toBasicResponse(Permission permission) {
        if (permission == null) {
            return null;
        }

        return PermissionResponse.builder()
                .id(permission.getId())
                .name(permission.getName())
                .description(permission.getDescription())
                .resource(permission.getResource())
                .action(permission.getAction())
                .build();
    }
}