package com.itss.auth.dto.response;

import lombok.Data;
import java.time.Instant;

@Data
public class InviteResponse {
    private Long id;
    private String email;
    private String role;
    private String status;
    private String invitedBy;
    private Instant expiresAt;
    private Instant createdAt;
} 
