package com.itss.portal.authentication.repository;

import com.itss.portal.authentication.model.RoleAudit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleAuditRepository extends JpaRepository<RoleAudit, Long> {
    // we can paginate/filter later if needed
}
