package com.itss.portal.authentication.dto;

public class InvitationRequest {
    private String email;
    private String roleName;

    public String getEmail() { return email; }
    public void setEmail(String e) { this.email = e; }

    public String getRoleName() { return roleName; }
    public void setRoleName(String r) { this.roleName = r; }
}
