package com.itss.portal.authentication.controller;

import com.itss.portal.authentication.dto.InvitationRequest;
import com.itss.portal.authentication.service.InvitationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invite")
public class InvitationController {

    private final InvitationService invitationService;

    public InvitationController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @PostMapping
    public ResponseEntity<?> invite(@RequestBody InvitationRequest request) {
        invitationService.invite(request.getEmail(), request.getRoleName());
        return ResponseEntity.ok("Invitation sent.");
    }

    @GetMapping("/accept")
    public ResponseEntity<?> accept(@RequestParam String token) {
        invitationService.accept(token);
        return ResponseEntity.ok("Invitation accepted; please complete signup.");
    }
}
