package com.itss.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableMethodSecurity(prePostEnabled = true)
@EnableTransactionManagement
public class AuthModuleApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthModuleApplication.class, args);
    }
} 
