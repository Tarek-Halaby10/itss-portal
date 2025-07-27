package com.itss.auth.mapper;

import com.itss.auth.entity.Role;
import com.itss.auth.entity.Permission;
import com.itss.auth.dto.request.RoleCreateRequest;
import com.itss.auth.dto.request.RoleUpdateRequest;
import com.itss.auth.dto.response.RoleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Role toEntity(RoleCreateRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Role toEntity(RoleUpdateRequest request);

    @Mapping(target = "permissions", expression = "java(mapPermissionsToStrings(role.getPermissions()))")
    RoleResponse toResponse(Role role);

    default Set<String> mapPermissionsToStrings(Set<Permission> permissions) {
        if (permissions == null) return null;
        return permissions.stream()
                .map(Permission::getName)
                .collect(Collectors.toSet());
    }
} 
