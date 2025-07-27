package com.itss.auth.mapper;

import com.itss.auth.entity.AuditLog;
import com.itss.auth.entity.User;
import com.itss.auth.dto.response.AuditLogResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AuditLogMapper {
    AuditLogMapper INSTANCE = Mappers.getMapper(AuditLogMapper.class);

    @Mapping(target = "user", expression = "java(mapUserToString(auditLog.getUser()))")
    AuditLogResponse toResponse(AuditLog auditLog);

    default String mapUserToString(User user) {
        return user != null ? user.getUsername() : null;
    }
} 
