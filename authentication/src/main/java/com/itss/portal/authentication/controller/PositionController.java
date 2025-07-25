package com.itss.portal.authentication.controller;

import com.itss.portal.authentication.model.Position;
import com.itss.portal.authentication.service.PositionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/admin/positions")
public class PositionController {

    private final PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @PostMapping
    public ResponseEntity<Position> createPosition(
            @RequestParam String name,
            @RequestParam(required = false) Long parentId,
            @RequestParam Set<String> permissions) {
        Position pos = positionService.create(name, parentId, permissions);
        return ResponseEntity.ok(pos);
    }

    // TODO: add update, delete, list endpoints
}
