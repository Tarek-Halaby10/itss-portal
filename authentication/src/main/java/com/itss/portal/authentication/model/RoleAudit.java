package com.itss.portal.authentication.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "role_audit")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** e.g. CREATE, UPDATE, DELETE */
    @Column(nullable = false)
    private String action;

    /** The role’s name that was changed */
    @Column(name = "role_name", nullable = false)
    private String roleName;

    /** Username of the actor who performed the change */
    @Column(name = "performed_by", nullable = false)
    private String performedBy;

    /** When the action occurred */
    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;
}
