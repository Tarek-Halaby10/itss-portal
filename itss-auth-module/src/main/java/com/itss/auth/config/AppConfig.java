// AppConfig.java - Place in: itss-auth-module/src/main/java/com/itss/auth/config/
package com.itss.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    private Long invitationTokenExpiration;
    private Long passwordResetTokenExpiration;
    private String baseUrl;
    private String frontendUrl;
    
    public AppConfig() {}
    
    // Getters and Setters
    public Long getInvitationTokenExpiration() { return invitationTokenExpiration; }
    public void setInvitationTokenExpiration(Long invitationTokenExpiration) { this.invitationTokenExpiration = invitationTokenExpiration; }
    
    public Long getPasswordResetTokenExpiration() { return passwordResetTokenExpiration; }
    public void setPasswordResetTokenExpiration(Long passwordResetTokenExpiration) { this.passwordResetTokenExpiration = passwordResetTokenExpiration; }
    
    public String getBaseUrl() { return baseUrl; }
    public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }
    
    public String getFrontendUrl() { return frontendUrl; }
    public void setFrontendUrl(String frontendUrl) { this.frontendUrl = frontendUrl; }
}