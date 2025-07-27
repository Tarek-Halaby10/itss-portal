package com.itss.auth.dto.response;

import lombok.Data;
import java.time.Instant;

@Data
public class AuditLogResponse {
    private Long id;
    private String user;
    private String action;
    private String endpoint;
    private String details;
    private Instant timestamp;
} 
