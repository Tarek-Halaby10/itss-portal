package com.itss.auth.mapper;

import com.itss.auth.entity.Permission;
import com.itss.auth.dto.response.PermissionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionMapper INSTANCE = Mappers.getMapper(PermissionMapper.class);

    PermissionResponse toResponse(Permission permission);
} 
