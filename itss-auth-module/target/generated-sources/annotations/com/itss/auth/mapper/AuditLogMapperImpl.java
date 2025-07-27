package com.itss.auth.mapper;

import com.itss.auth.dto.response.AuditLogResponse;
import com.itss.auth.entity.AuditLog;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-28T01:43:44+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class AuditLogMapperImpl implements AuditLogMapper {

    @Override
    public AuditLogResponse toResponse(AuditLog auditLog) {
        if ( auditLog == null ) {
            return null;
        }

        AuditLogResponse auditLogResponse = new AuditLogResponse();

        auditLogResponse.setId( auditLog.getId() );
        auditLogResponse.setAction( auditLog.getAction() );
        auditLogResponse.setEndpoint( auditLog.getEndpoint() );
        auditLogResponse.setDetails( auditLog.getDetails() );
        auditLogResponse.setTimestamp( auditLog.getTimestamp() );

        auditLogResponse.setUser( mapUserToString(auditLog.getUser()) );

        return auditLogResponse;
    }
}
