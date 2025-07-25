package com.itss.portal.authentication.dto;

import jakarta.validation.constraints.NotBlank;

public class TokenRefreshRequest {
    @NotBlank
    private String refreshToken;
    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String t) { refreshToken = t; }
}
