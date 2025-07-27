package com.itss.auth.service.impl;

import com.itss.auth.entity.AuditLog;
import com.itss.auth.entity.User;
import com.itss.auth.repository.AuditLogRepository;
import com.itss.auth.repository.UserRepository;
import com.itss.auth.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuditLogServiceImpl implements AuditLogService {
    @Autowired
    private AuditLogRepository auditLogRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void logAction(Long userId, String action, String endpoint, String details) {
        User user = userRepository.findById(userId).orElse(null);
        AuditLog log = AuditLog.builder()
                .user(user)
                .action(action)
                .endpoint(endpoint)
                .details(details)
                .build();
        auditLogRepository.save(log);
    }

    @Override
    public List<AuditLog> getAllLogs() {
        return auditLogRepository.findAll();
    }

    @Override
    public List<AuditLog> getLogsByUser(Long userId) {
        return auditLogRepository.findAll().stream()
                .filter(log -> log.getUser() != null && log.getUser().getId().equals(userId))
                .collect(Collectors.toList());
    }
} 
