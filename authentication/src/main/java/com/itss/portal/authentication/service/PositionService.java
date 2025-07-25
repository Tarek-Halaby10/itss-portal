package com.itss.portal.authentication.service;

import com.itss.portal.authentication.exception.EntityNotFoundException;
import com.itss.portal.authentication.model.Position;
import com.itss.portal.authentication.repository.PositionRepository;
import com.itss.portal.authentication.repository.PermissionRepository;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PositionService {
  private final PositionRepository posRepo;
  private final PermissionRepository permRepo;
  private final AuditService audit;

  public PositionService(PositionRepository posRepo,
                         PermissionRepository permRepo,
                         AuditService audit) {
    this.posRepo = posRepo;
    this.permRepo = permRepo;
    this.audit = audit;
  }

  public Position create(String name, Long parentId, Set<String> permNames) {
    Position p = new Position();
    p.setName(name);
    if (parentId != null) {
      Position parent = posRepo.findById(parentId)
                       .orElseThrow(() -> new EntityNotFoundException("Parent Position"));
      p.setParentPosition(parent);
    }
    var perms = permNames.stream()
                  .map(n -> permRepo.findByName(n)
                      .orElseThrow(() -> new EntityNotFoundException("Permission "+n)))
                  .collect(Collectors.toSet());
    p.setPermissions(perms);
    posRepo.save(p);
    audit.log(null, "CREATE_POSITION", "pos="+name);
    return p;
  }

  // update, delete, list omitted
}
