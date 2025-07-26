// InviteUserRequest.java - Place in: itss-auth-module/src/main/java/com/itss/auth/dto/request/
package com.itss.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class InviteUserRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    
    @NotNull(message = "Role ID is required")
    private Long roleId;
    
    private String message;
    
    public InviteUserRequest() {}
    
    public InviteUserRequest(String email, Long roleId, String message) {
        this.email = email;
        this.roleId = roleId;
        this.message = message;
    }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public Long getRoleId() { return roleId; }
    public void setRoleId(Long roleId) { this.roleId = roleId; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}