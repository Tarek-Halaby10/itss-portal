package com.itss.auth.dto.response;

import lombok.Data;
import java.time.Instant;

@Data
public class PasswordResetTokenResponse {
    private Long id;
    private String user;
    private String token;
    private Instant expiresAt;
    private Instant createdAt;
    private boolean used;
} 
