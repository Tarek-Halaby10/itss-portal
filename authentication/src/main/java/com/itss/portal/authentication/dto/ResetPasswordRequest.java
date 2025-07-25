package com.itss.portal.authentication.dto;

import com.itss.portal.authentication.validation.ValidPassword;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ResetPasswordRequest {
	@NotBlank
    private String token;

	@ValidPassword
    private String newPassword;

    public String getToken() { return token; }
    public void setToken(String t) { this.token = t; }

    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String p) { this.newPassword = p; }
}
