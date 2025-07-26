package com.itss.auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_activities")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserActivity {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "action", nullable = false, length = 100)
    private String action;

    @Column(name = "resource", length = 50)
    private String resource;

    @Column(name = "resource_id")
    private Long resourceId;

    @Column(name = "details", columnDefinition = "TEXT")
    private String details;

    @Column(name = "ip_address", columnDefinition = "INET")
    private String ipAddress;

    @Column(name = "user_agent", columnDefinition = "TEXT")
    private String userAgent;

    @Column(name = "success", nullable = false)
    @Builder.Default
    private Boolean success = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Helper methods for common activities
    public static UserActivity loginSuccess(User user, String ipAddress, String userAgent) {
        return UserActivity.builder()
                .user(user)
                .action("LOGIN_SUCCESS")
                .details("User logged in successfully")
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .success(true)
                .build();
    }

    public static UserActivity loginFailed(User user, String ipAddress, String userAgent, String reason) {
        return UserActivity.builder()
                .user(user)
                .action("LOGIN_FAILED")
                .details("Login attempt failed: " + reason)
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .success(false)
                .build();
    }

    public static UserActivity passwordReset(User user, String ipAddress) {
        return UserActivity.builder()
                .user(user)
                .action("PASSWORD_RESET")
                .details("Password reset successfully")
                .ipAddress(ipAddress)
                .success(true)
                .build();
    }

    public static UserActivity profileUpdate(User user, String ipAddress, String updatedFields) {
        return UserActivity.builder()
                .user(user)
                .action("PROFILE_UPDATE")
                .details("Profile updated: " + updatedFields)
                .ipAddress(ipAddress)
                .success(true)
                .build();
    }

    public static UserActivity createActivity(User user, String action, String resource, Long resourceId, 
                                            String details, String ipAddress, String userAgent, boolean success) {
        return UserActivity.builder()
                .user(user)
                .action(action)
                .resource(resource)
                .resourceId(resourceId)
                .details(details)
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .success(success)
                .build();
    }

    // Utility methods
    public boolean isSecurityRelated() {
        if (action == null) return false;
        return action.contains("LOGIN") || 
               action.contains("PASSWORD") || 
               action.contains("PERMISSION") || 
               action.contains("ROLE") ||
               action.contains("SECURITY");
    }

    public boolean isSuccessful() {
        return Boolean.TRUE.equals(success);
    }
}
