package com.itss.portal.authentication.controller;

import com.itss.portal.authentication.model.ActivityLog;
import com.itss.portal.authentication.repository.ActivityLogRepository;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/activity")
public class ActivityController {

    private final ActivityLogRepository logRepo;

    public ActivityController(ActivityLogRepository logRepo) {
        this.logRepo = logRepo;
    }

    @GetMapping
    public ResponseEntity<Page<ActivityLog>> getActivity(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageReq = PageRequest.of(page, size, Sort.by("timestamp").descending());
        return ResponseEntity.ok(logRepo.findAll(pageReq));
    }
}
