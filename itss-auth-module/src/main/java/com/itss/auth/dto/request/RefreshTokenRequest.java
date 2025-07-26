// RefreshTokenRequest.java - Place in: itss-auth-module/src/main/java/com/itss/auth/dto/request/
package com.itss.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public class RefreshTokenRequest {
    @NotBlank(message = "Refresh token is required")
    private String refreshToken;
    
    public RefreshTokenRequest() {}
    
    public RefreshTokenRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    
    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
}