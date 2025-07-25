package com.itss.portal.authentication.controller;

import com.itss.portal.authentication.model.RoleAudit;
import com.itss.portal.authentication.repository.RoleAuditRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/audit/roles")
public class RoleAuditController {

    private final RoleAuditRepository auditRepo;

    public RoleAuditController(RoleAuditRepository auditRepo) {
        this.auditRepo = auditRepo;
    }

    /** 
     * GET /api/admin/audit/roles?page=0&size=20 
     */
    @GetMapping
    public ResponseEntity<Page<RoleAudit>> listAudits(Pageable pageable) {
        Page<RoleAudit> page = auditRepo.findAll(pageable);
        return ResponseEntity.ok(page);
    }
}
