package com.itss.portal.authentication.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "users")
@Data                 // generates getters, setters, toString, equals, hashCode
@NoArgsConstructor          // generates a no‑arg constructor
@AllArgsConstructor         // generates an all‑args constructor
public class User {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private boolean enabled = false; // approved flag

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    
    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

    // getters and setters
}
