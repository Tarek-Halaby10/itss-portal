package com.itss.portal.authentication.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "permissions")
@Data                       // generates getters, setters, toString, equals, hashCode
@NoArgsConstructor          // generates a no‑arg constructor
@AllArgsConstructor         // generates an all‑args constructor
public class Permission {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    // getters and setters
}
