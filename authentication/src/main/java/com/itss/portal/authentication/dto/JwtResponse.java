package com.itss.portal.authentication.dto;

public class JwtResponse {
    private final String accessToken;
    private final String refreshToken;

    public JwtResponse(String a, String r) {
        this.accessToken = a;
        this.refreshToken = r;
    }

    public String getAccessToken() { return accessToken; }
    public String getRefreshToken() { return refreshToken; }
}
