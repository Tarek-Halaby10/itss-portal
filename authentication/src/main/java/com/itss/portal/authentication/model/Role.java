package com.itss.portal.authentication.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data                       // generates getters, setters, toString, equals, hashCode
@NoArgsConstructor          // generates a no‑arg constructor
@AllArgsConstructor         // generates an all‑args constructor
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
      name = "role_permissions",
      joinColumns = @JoinColumn(name = "role_id"),
      inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions = new HashSet<>();

    private boolean templateFlag = false;
    public Role(String name, String description, Set<Permission> perms, boolean templateFlag) {
        this.name = name;
        this.description = description;
        this.permissions = perms;
        this.templateFlag = templateFlag;
    }
    // getters and setters
}
