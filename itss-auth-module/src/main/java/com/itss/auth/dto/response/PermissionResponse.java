// PermissionResponse.java - Place in: itss-auth-module/src/main/java/com/itss/auth/dto/response/
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
public class PermissionResponse {
    private Long id;
    private String name;
    private String description;
    private String resource;
    private String action;
    private String endpoint;
    private LocalDateTime createdAt;
    
    
}