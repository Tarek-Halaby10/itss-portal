package com.itss.auth.service;

import com.itss.auth.entity.AuditLog;
import java.util.List;

public interface AuditLogService {
    void logAction(Long userId, String action, String endpoint, String details);
    List<AuditLog> getAllLogs();
    List<AuditLog> getLogsByUser(Long userId);
} 
