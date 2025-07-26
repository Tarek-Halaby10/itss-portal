// UserActivityRepository.java - Place in: itss-auth-module/src/main/java/com/itss/auth/repository/
package com.itss.auth.repository;

import com.itss.auth.entity.User;
import com.itss.auth.entity.UserActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserActivityRepository extends JpaRepository<UserActivity, Long> {
    
    List<UserActivity> findByUser(User user);
    
    Page<UserActivity> findByUser(User user, Pageable pageable);
    
    List<UserActivity> findByAction(String action);
    
    List<UserActivity> findByResource(String resource);
    
    @Query("SELECT ua FROM UserActivity ua WHERE " +
           "(:user IS NULL OR ua.user = :user) AND " +
           "(:action IS NULL OR LOWER(ua.action) LIKE LOWER(CONCAT('%', :action, '%'))) AND " +
           "(:resource IS NULL OR LOWER(ua.resource) LIKE LOWER(CONCAT('%', :resource, '%'))) AND " +
           "(:success IS NULL OR ua.success = :success) AND " +
           "(:startDate IS NULL OR ua.createdAt >= :startDate) AND " +
           "(:endDate IS NULL OR ua.createdAt <= :endDate)")
    Page<UserActivity> findActivitiesWithFilters(
        @Param("user") User user,
        @Param("action") String action,
        @Param("resource") String resource,
        @Param("success") Boolean success,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate,
        Pageable pageable
    );
    
    @Query("SELECT ua FROM UserActivity ua WHERE ua.createdAt BETWEEN :startDate AND :endDate ORDER BY ua.createdAt DESC")
    List<UserActivity> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT COUNT(ua) FROM UserActivity ua WHERE ua.user = :user AND ua.createdAt >= :since")
    long countUserActivitiesSince(@Param("user") User user, @Param("since") LocalDateTime since);
    
    @Query("SELECT ua.action, COUNT(ua) FROM UserActivity ua WHERE ua.createdAt >= :since GROUP BY ua.action ORDER BY COUNT(ua) DESC")
    List<Object[]> getTopActionsSince(@Param("since") LocalDateTime since);
    
    @Query("SELECT ua.resource, COUNT(ua) FROM UserActivity ua WHERE ua.createdAt >= :since GROUP BY ua.resource ORDER BY COUNT(ua) DESC")
    List<Object[]> getTopResourcesSince(@Param("since") LocalDateTime since);
}