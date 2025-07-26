// UserResponse.java - Place in: itss-auth-module/src/main/java/com/itss/auth/dto/response/
package com.itss.auth.dto.response;

import com.itss.auth.entity.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private UserStatus status;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<RoleResponse> roles;
    
   
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
}