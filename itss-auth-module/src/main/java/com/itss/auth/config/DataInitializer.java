package com.itss.auth.config;

import com.itss.auth.entity.Permission;
import com.itss.auth.entity.Role;
import com.itss.auth.entity.User;
import com.itss.auth.repository.PermissionRepository;
import com.itss.auth.repository.RoleRepository;
import com.itss.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.HashSet;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initializePermissions();
        initializeRoles();
        initializeSuperAdmin();
    }

    private void initializePermissions() {
        String[] permissions = {
            "VIEW_USERS", "BLOCK_USER", "CREATE_ROLE", "UPDATE_ROLE", 
            "DELETE_ROLE", "VIEW_ROLES", "ASSIGN_PERMISSIONS", "VIEW_AUDIT_LOGS"
        };

        for (String permName : permissions) {
            if (!permissionRepository.existsByName(permName)) {
                Permission permission = Permission.builder()
                    .name(permName)
                    .description("Permission to " + permName.toLowerCase().replace("_", " "))
                    .build();
                permissionRepository.save(permission);
            }
        }
    }

    private void initializeRoles() {
        if (!roleRepository.existsByName("SUPER_ADMIN")) {
            Set<Permission> allPermissions = new HashSet<>(permissionRepository.findAll());
            Role superAdminRole = Role.builder()
                .name("SUPER_ADMIN")
                .description("Super Administrator with all permissions")
                .permissions(allPermissions)
                .build();
            roleRepository.save(superAdminRole);
        }

        if (!roleRepository.existsByName("USER")) {
            Role userRole = Role.builder()
                .name("USER")
                .description("Basic user role")
                .permissions(new HashSet<>())
                .build();
            roleRepository.save(userRole);
        }
    }

    private void initializeSuperAdmin() {
        if (!userRepository.existsByUsername("admin")) {
            Role superAdminRole = roleRepository.findByName("SUPER_ADMIN").orElseThrow();
            User superAdmin = User.builder()
                .username("admin")
                .email("admin@itss.com")
                .password(passwordEncoder.encode("admin"))
                .status(User.Status.APPROVED)
                .roles(Set.of(superAdminRole))
                .build();
            userRepository.save(superAdmin);
        }
    }
} 
