package com.itss.portal.authentication.config;

import com.itss.portal.authentication.model.*;
import com.itss.portal.authentication.repository.*;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataInitializer {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PermissionRepository permRepo;
    private final PasswordEncoder encoder;

    public DataInitializer(UserRepository userRepo,
                           RoleRepository roleRepo,
                           PermissionRepository permRepo,
                           PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.permRepo = permRepo;
        this.encoder = encoder;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() {
        // Create default permissions if none exist
        if (permRepo.count() == 0) {
            for (String name : new String[]{
                    "APPROVE_USERS","INVITE_USERS","RESET_PASSWORD",
                    "VIEW_USERS","BLOCK_USERS","MANAGE_ROLES","MANAGE_POSITIONS"
            }) {
                Permission p = new Permission();
                p.setName(name);
                p.setDescription(name);
                permRepo.save(p);
            }
        }

        // Create SUPER_ADMIN role if absent
        Role superRole = roleRepo.findByName("SUPER_ADMIN")
            .orElseGet(() -> {
                Role r = new Role();
                r.setName("SUPER_ADMIN");
                r.setDescription("Super administrator");
                r.setPermissions(Set.copyOf(permRepo.findAll()));
                r.setTemplateFlag(true);
                return roleRepo.save(r);
            });

        // Create admin user if absent
        if (!userRepo.findByUsername("admin").isPresent()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@localhost");
            admin.setPasswordHash(encoder.encode("admin"));
            admin.setEnabled(true);
            admin.setRole(superRole);
            // No position yet—could set a default if desired
            userRepo.save(admin);
        }
    }
}
