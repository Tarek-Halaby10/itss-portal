// RoleResponse.java - Place in: itss-auth-module/src/main/java/com/itss/auth/dto/response/
package com.itss.auth.dto.response;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse {
    private Long id;
    private String name;
    private String description;
    private Boolean isTemplate;
    private Boolean isSystemRole;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<PermissionResponse> permissions;
    private Long userCount;
    
    
}