package com.itss.portal.authentication.service;

import com.itss.portal.authentication.model.ActivityLog;
import com.itss.portal.authentication.model.User;
import com.itss.portal.authentication.repository.ActivityLogRepository;
import org.springframework.stereotype.Service;
import java.time.Instant;
import com.itss.portal.authentication.security.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuditService {
  private final ActivityLogRepository logRepo;
  private static final Logger loger = LoggerFactory.getLogger(AuthService.class);

  public AuditService(ActivityLogRepository logRepo) {
    this.logRepo = logRepo;
  }

  public void log(User user, String action, String metadata) {
    ActivityLog log = new ActivityLog();
    log.setUser(user);
    log.setActionType(action);
    log.setMetadata(metadata);
    log.setTimestamp(Instant.now());
    logRepo.save(log);
    loger.info(action);
  }
}
