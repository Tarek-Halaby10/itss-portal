// UserRepository.java - Place in: itss-auth-module/src/main/java/com/itss/auth/repository/
package com.itss.auth.repository;

import com.itss.auth.entity.User;
import com.itss.auth.entity.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    
    Optional<User> findByEmail(String email);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    List<User> findByStatus(UserStatus status);
    
    Page<User> findByStatus(UserStatus status, Pageable pageable);
    
    @Query("SELECT u FROM User u WHERE " +
           "(:username IS NULL OR LOWER(u.username) LIKE LOWER(CONCAT('%', :username, '%'))) AND " +
           "(:email IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND " +
           "(:firstName IS NULL OR LOWER(u.firstName) LIKE LOWER(CONCAT('%', :firstName, '%'))) AND " +
           "(:lastName IS NULL OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :lastName, '%'))) AND " +
           "(:status IS NULL OR u.status = :status)")
    Page<User> findUsersWithFilters(
        @Param("username") String username,
        @Param("email") String email,
        @Param("firstName") String firstName,
        @Param("lastName") String lastName,
        @Param("status") UserStatus status,
        Pageable pageable
    );
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.status = :status")
    long countByStatus(@Param("status") UserStatus status);
    
    @Modifying
    @Query("UPDATE User u SET u.lastLoginAt = :loginTime WHERE u.id = :userId")
    void updateLastLoginTime(@Param("userId") Long userId, @Param("loginTime") LocalDateTime loginTime);
    
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.id = :roleId")
    List<User> findByRoleId(@Param("roleId") Long roleId);
    
    @Query("SELECT u FROM User u WHERE u.createdAt BETWEEN :startDate AND :endDate")
    List<User> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}