package com.itss.auth.service;

public interface EmailService {
    void sendInviteEmail(String to, String token, String roleName);
    void sendPasswordResetEmail(String to, String token);
} 
