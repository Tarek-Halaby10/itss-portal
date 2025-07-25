package com.itss.portal.authentication.repository;

import com.itss.portal.authentication.model.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
    // paging and sorting comes built-in
}
