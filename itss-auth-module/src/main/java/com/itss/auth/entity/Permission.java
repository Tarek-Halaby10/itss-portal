// Permission.java - Place in: itss-auth-module/src/main/java/com/itss/auth/entity/
package com.itss.auth.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "permissions")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @NotBlank
    @Size(max = 50)
    @Column(name = "resource", nullable = false)
    private String resource;

    @NotBlank
    @Size(max = 20)
    @Column(name = "action", nullable = false)
    private String action;

    @Size(max = 200)
    @Column(name = "endpoint")
    private String endpoint;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<>();

    // Constructors
    public Permission() {}

    public Permission(String name, String description, String resource, String action, String endpoint) {
        this.name = name;
        this.description = description;
        this.resource = resource;
        this.action = action;
        this.endpoint = endpoint;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getResource() { return resource; }
    public void setResource(String resource) { this.resource = resource; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getEndpoint() { return endpoint; }
    public void setEndpoint(String endpoint) { this.endpoint = endpoint; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }
}