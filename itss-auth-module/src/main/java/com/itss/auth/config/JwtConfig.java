// JwtConfig.java - Place in: itss-auth-module/src/main/java/com/itss/auth/config/
package com.itss.auth.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    private String secret;
    private Long accessTokenExpiration;
    private Long refreshTokenExpiration;
    
    public JwtConfig() {}
    
    // Getters and Setters
    public String getSecret() { return secret; }
    public void setSecret(String secret) { this.secret = secret; }
    
    public Long getAccessTokenExpiration() { return accessTokenExpiration; }
    public void setAccessTokenExpiration(Long accessTokenExpiration) { this.accessTokenExpiration = accessTokenExpiration; }
    
    public Long getRefreshTokenExpiration() { return refreshTokenExpiration; }
    public void setRefreshTokenExpiration(Long refreshTokenExpiration) { this.refreshTokenExpiration = refreshTokenExpiration; }
}