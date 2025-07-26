// UpdateRolePermissionsRequest.java - Place in: itss-auth-module/src/main/java/com/itss/auth/dto/request/
package com.itss.auth.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.Set;

public class UpdateRolePermissionsRequest {
    @NotNull(message = "Permission IDs are required")
    private Set<Long> permissionIds;
    
    public UpdateRolePermissionsRequest() {}
    
    public UpdateRolePermissionsRequest(Set<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }
    
    public Set<Long> getPermissionIds() { return permissionIds; }
    public void setPermissionIds(Set<Long> permissionIds) { this.permissionIds = permissionIds; }
}