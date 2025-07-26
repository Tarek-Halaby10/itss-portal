// InvitationResponse.java - Place in: itss-auth-module/src/main/java/com/itss/auth/dto/response/
package com.itss.auth.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvitationResponse {
    private Long id;
    private String email;
    private RoleResponse role;
    private Boolean used;
    private LocalDateTime expiresAt;
    private LocalDateTime acceptedAt;
    private LocalDateTime createdAt;
    private String createdByName;
    private Boolean isExpired;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }
}
