package com.itss.auth.service.impl;

import com.itss.auth.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;
    
    @Value("${app.frontend-url}")
    private String frontendUrl;

    @Override
    public void sendInviteEmail(String to, String token, String roleName) {
        try {
            Context context = new Context();
            context.setVariable("token", token);
            context.setVariable("roleName", roleName);
            context.setVariable("acceptUrl", frontendUrl + "/accept-invite?token=" + token);
            
            String content = templateEngine.process("invite-email", context);
            
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject("You're invited to ITSS Portal");
            helper.setText(content, true);
            
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send invite email", e);
        }
    }

    @Override
    public void sendPasswordResetEmail(String to, String token) {
        try {
            Context context = new Context();
            context.setVariable("token", token);
            context.setVariable("resetUrl", frontendUrl + "/reset-password?token=" + token);
            
            String content = templateEngine.process("password-reset-email", context);
            
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject("Password Reset Request");
            helper.setText(content, true);
            
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send password reset email", e);
        }
    }
} 
