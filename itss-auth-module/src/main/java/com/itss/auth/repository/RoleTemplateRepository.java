package com.itss.auth.repository;

import com.itss.auth.entity.RoleTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleTemplateRepository extends JpaRepository<RoleTemplate, Long> {
    Optional<RoleTemplate> findByName(String name);
    boolean existsByName(String name);
} 
