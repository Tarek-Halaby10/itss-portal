// RoleRepository.java - Place in: itss-auth-module/src/main/java/com/itss/auth/repository/
package com.itss.auth.repository;

import com.itss.auth.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    
    Optional<Role> findByName(String name);
    
    boolean existsByName(String name);
    
    List<Role> findByIsTemplate(Boolean isTemplate);
    
    List<Role> findByIsSystemRole(Boolean isSystemRole);
    
    @Query("SELECT r FROM Role r WHERE " +
           "(:name IS NULL OR LOWER(r.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:isTemplate IS NULL OR r.isTemplate = :isTemplate) AND " +
           "(:isSystemRole IS NULL OR r.isSystemRole = :isSystemRole)")
    Page<Role> findRolesWithFilters(
        @Param("name") String name,
        @Param("isTemplate") Boolean isTemplate,
        @Param("isSystemRole") Boolean isSystemRole,
        Pageable pageable
    );
    
    @Query("SELECT r FROM Role r JOIN r.users u WHERE u.id = :userId")
    List<Role> findByUserId(@Param("userId") Long userId);
    
    @Query("SELECT COUNT(u) FROM User u JOIN u.roles r WHERE r.id = :roleId")
    long countUsersByRoleId(@Param("roleId") Long roleId);
}