// UserActivityResponse.java - Place in: itss-auth-module/src/main/java/com/itss/auth/dto/response/
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
public class UserActivityResponse {
    private Long id;
    private Long userId;
    private String username;
    private String userFullName;
    private String action;
    private String resource;
    private Long resourceId;
    private String details;
    private String ipAddress;
    private String userAgent;
    private Boolean success;
    private LocalDateTime createdAt;
    
    
}