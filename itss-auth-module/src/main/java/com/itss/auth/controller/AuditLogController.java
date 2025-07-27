package com.itss.auth.controller;

import com.itss.auth.service.AuditLogService;
import com.itss.auth.entity.AuditLog;
import com.itss.auth.mapper.AuditLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/audit-logs")
public class AuditLogController {
    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private AuditLogMapper auditLogMapper;

    @GetMapping
    @PreAuthorize("hasAuthority(T(com.itss.auth.security.PermissionConstants).VIEW_AUDIT_LOGS)")
    public ResponseEntity<?> getAllLogs() {
        try {
            List<AuditLog> logs = auditLogService.getAllLogs();
            return ResponseEntity.ok(logs.stream().map(auditLogMapper::toResponse).toList());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAuthority(T(com.itss.auth.security.PermissionConstants).VIEW_AUDIT_LOGS)")
    public ResponseEntity<?> getLogsByUser(@PathVariable Long userId) {
        try {
            List<AuditLog> logs = auditLogService.getLogsByUser(userId);
            return ResponseEntity.ok(logs.stream().map(auditLogMapper::toResponse).toList());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
} 
