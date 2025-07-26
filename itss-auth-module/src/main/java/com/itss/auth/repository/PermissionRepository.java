// PermissionRepository.java - Place in: itss-auth-module/src/main/java/com/itss/auth/repository/
package com.itss.auth.repository;

import com.itss.auth.entity.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    
    Optional<Permission> findByName(String name);
    
    boolean existsByName(String name);
    
    List<Permission> findByResource(String resource);
    
    List<Permission> findByAction(String action);
    
    List<Permission> findByResourceAndAction(String resource, String action);
    
    @Query("SELECT p FROM Permission p WHERE " +
           "(:resource IS NULL OR LOWER(p.resource) LIKE LOWER(CONCAT('%', :resource, '%'))) AND " +
           "(:action IS NULL OR LOWER(p.action) LIKE LOWER(CONCAT('%', :action, '%'))) AND " +
           "(:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))")
    Page<Permission> findPermissionsWithFilters(
        @Param("resource") String resource,
        @Param("action") String action,
        @Param("name") String name,
        Pageable pageable
    );
    
    @Query("SELECT p FROM Permission p JOIN p.roles r WHERE r.id = :roleId")
    List<Permission> findByRoleId(@Param("roleId") Long roleId);
    
    @Query("SELECT DISTINCT p FROM Permission p " +
           "JOIN p.roles r " +
           "JOIN r.users u " +
           "WHERE u.id = :userId")
    Set<Permission> findPermissionsByUserId(@Param("userId") Long userId);
    
    @Query("SELECT p.name FROM Permission p " +
           "JOIN p.roles r " +
           "JOIN r.users u " +
           "WHERE u.id = :userId")
    Set<String> findPermissionNamesByUserId(@Param("userId") Long userId);
}