package com.itss.portal.authentication.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "activity_logs")
@Data                       // generates getters, setters, toString, equals, hashCode
@NoArgsConstructor          // generates a no‑arg constructor
@AllArgsConstructor         // generates an all‑args constructor
public class ActivityLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String actionType;

    @Column(columnDefinition = "TEXT")
    private String metadata;

    @Column(nullable = false)
    private Instant timestamp = Instant.now();

    // getters and setters
}
