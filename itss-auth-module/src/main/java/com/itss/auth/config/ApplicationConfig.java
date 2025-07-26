// ApplicationConfig.java - Place in: itss-auth-module/src/main/java/com/itss/auth/config/
package com.itss.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
public class ApplicationConfig {
    private String name;
    private String version;
    private String frontendUrl;
    private Invitation invitation = new Invitation();
    private PasswordReset passwordReset = new PasswordReset();
    
    public static class Invitation {
        private long expirationHours = 72; // 3 days
        
        public long getExpirationHours() { return expirationHours; }
        public void setExpirationHours(long expirationHours) { this.expirationHours = expirationHours; }
    }
    
    public static class PasswordReset {
        private long expirationMinutes = 30; // 30 minutes
        
        public long getExpirationMinutes() { return expirationMinutes; }
        public void setExpirationMinutes(long expirationMinutes) { this.expirationMinutes = expirationMinutes; }
    }
    
    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
    
    public String getFrontendUrl() { return frontendUrl; }
    public void setFrontendUrl(String frontendUrl) { this.frontendUrl = frontendUrl; }
    
    public Invitation getInvitation() { return invitation; }
    public void setInvitation(Invitation invitation) { this.invitation = invitation; }
    
    public PasswordReset getPasswordReset() { return passwordReset; }
    public void setPasswordReset(PasswordReset passwordReset) { this.passwordReset = passwordReset; }
}