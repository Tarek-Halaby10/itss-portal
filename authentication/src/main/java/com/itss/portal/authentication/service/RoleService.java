package com.itss.portal.authentication.service;

import com.itss.portal.authentication.config.AuthenticationFacade;
import com.itss.portal.authentication.exception.EntityNotFoundException;
import com.itss.portal.authentication.model.Role;
import com.itss.portal.authentication.model.RoleAudit;
import com.itss.portal.authentication.repository.RoleRepository;
import com.itss.portal.authentication.model.Permission;
import com.itss.portal.authentication.repository.PermissionRepository;
import com.itss.portal.authentication.repository.RoleAuditRepository;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService {
  private final RoleRepository roleRepo;
  private final PermissionRepository permRepo;
  private final AuditService audit;
  private final RoleAuditRepository auditRepo;
  private final AuthenticationFacade authFacade; // to get current username


  public RoleService(RoleRepository roleRepo,
                     PermissionRepository permRepo,
                     AuditService audit,
                     RoleAuditRepository auditRepo,
                     AuthenticationFacade authFacade) {
    this.roleRepo = roleRepo;
    this.permRepo = permRepo;
    this.audit = audit;
    this.auditRepo    = auditRepo;
    this.authFacade   = authFacade;
  }

//1. Accept Set<String> instead of Set<Permission>
public Role createRole(String name,
                      String description,
                      Set<String> permNames,
                      boolean template) {

   // 2. Lookup Permission entities
   Set<Permission> perms = permNames.stream()
       .map(pn -> permRepo.findByName(pn)
             .orElseThrow(() -> new EntityNotFoundException("Permission: " + pn)))
       .collect(Collectors.toSet());

   // 3. Build the Role (via no‑arg + setters)
   Role r = new Role();
   r.setName(name);
   r.setDescription(description);
   r.setPermissions(perms);
   r.setTemplateFlag(template);

   roleRepo.save(r);
   audit("CREATE", r.getName());
   return r;
}

public Role updateRole(Long id,
        String description,
        Set<String> permNames,
        boolean template) {
Role r = roleRepo.findById(id)
.orElseThrow(() -> new EntityNotFoundException("Role"));

Set<Permission> perms = permNames.stream()
.map(pn -> permRepo.findByName(pn)
.orElseThrow(() -> new EntityNotFoundException("Permission: " + pn)))
.collect(Collectors.toSet());

r.setDescription(description);
r.setPermissions(perms);
r.setTemplateFlag(template);

roleRepo.save(r);
audit("UPDATE", r.getName());
return r;
}

  public void deleteRole(Long id) {
      Role r = roleRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role"));
      roleRepo.delete(r);

      audit("DELETE", r.getName());
  }

  private void audit(String action, String roleName) {
      RoleAudit entry = new RoleAudit();
      entry.setAction(action);
      entry.setRoleName(roleName);
      entry.setPerformedBy(authFacade.getCurrentUsername());
      entry.setTimestamp(Instant.now());
      auditRepo.save(entry);
  }

  // update, delete, list omitted for brevity
}
