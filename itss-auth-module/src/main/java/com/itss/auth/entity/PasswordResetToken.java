// PasswordResetToken.java - Place in: itss-auth-module/src/main/java/com/itss/auth/entity/
package com.itss.auth.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "password_reset_tokens")
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "used")
    private Boolean used = false;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Constructors
    public PasswordResetToken() {}

    public PasswordResetToken(String token, User user, LocalDateTime expiresAt) {
        this.token = token;
        this.user = user;
        this.expiresAt = expiresAt;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }

    public Boolean getUsed() { return used; }
    public void setUsed(Boolean used) { this.used = used; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }
}