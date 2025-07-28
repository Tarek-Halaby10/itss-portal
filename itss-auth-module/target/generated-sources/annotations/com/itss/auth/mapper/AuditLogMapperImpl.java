package com.itss.auth.mapper;

import com.itss.auth.dto.response.AuditLogResponse;
import com.itss.auth.entity.AuditLog;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-28T19:00:29+0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250628-1110, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class AuditLogMapperImpl implements AuditLogMapper {

    @Override
    public AuditLogResponse toResponse(AuditLog auditLog) {
        if ( auditLog == null ) {
            return null;
        }

        AuditLogResponse auditLogResponse = new AuditLogResponse();

        auditLogResponse.setAction( auditLog.getAction() );
        auditLogResponse.setDetails( auditLog.getDetails() );
        auditLogResponse.setEndpoint( auditLog.getEndpoint() );
        auditLogResponse.setId( auditLog.getId() );
        auditLogResponse.setTimestamp( auditLog.getTimestamp() );

        auditLogResponse.setUser( mapUserToString(auditLog.getUser()) );

        return auditLogResponse;
    }
}
