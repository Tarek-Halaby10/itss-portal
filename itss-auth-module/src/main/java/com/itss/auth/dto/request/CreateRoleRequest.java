// CreateRoleRequest.java - Place in: itss-auth-module/src/main/java/com/itss/auth/dto/request/
package com.itss.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class CreateRoleRequest {
    @NotBlank(message = "Role name is required")
    @Size(max = 50, message = "Role name must not exceed 50 characters")
    private String name;
    
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;
    
    private Boolean isTemplate = false;
    
    private Set<Long> permissionIds;
    
    public CreateRoleRequest() {}
    
    public CreateRoleRequest(String name, String description, Boolean isTemplate, Set<Long> permissionIds) {
        this.name = name;
        this.description = description;
        this.isTemplate = isTemplate;
        this.permissionIds = permissionIds;
    }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Boolean getIsTemplate() { return isTemplate; }
    public void setIsTemplate(Boolean isTemplate) { this.isTemplate = isTemplate; }
    
    public Set<Long> getPermissionIds() { return permissionIds; }
    public void setPermissionIds(Set<Long> permissionIds) { this.permissionIds = permissionIds; }
}