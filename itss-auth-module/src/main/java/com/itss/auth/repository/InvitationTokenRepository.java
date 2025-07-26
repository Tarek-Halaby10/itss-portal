// InvitationTokenRepository.java - Place in: itss-auth-module/src/main/java/com/itss/auth/repository/
package com.itss.auth.repository;

import com.itss.auth.entity.InvitationToken;
import com.itss.auth.entity.User;
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
public interface InvitationTokenRepository extends JpaRepository<InvitationToken, Long> {
    
    Optional<InvitationToken> findByToken(String token);
    
    @Query("SELECT it FROM InvitationToken it WHERE it.token = :token AND it.used = false AND it.expiresAt > :now")
    Optional<InvitationToken> findValidToken(@Param("token") String token, @Param("now") LocalDateTime now);
    
    List<InvitationToken> findByEmail(String email);
    
    List<InvitationToken> findByCreatedBy(User createdBy);
    
    Page<InvitationToken> findByCreatedBy(User createdBy, Pageable pageable);
    
    @Query("SELECT it FROM InvitationToken it WHERE " +
           "(:email IS NULL OR LOWER(it.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND " +
           "(:used IS NULL OR it.used = :used) AND " +
           "(:createdBy IS NULL OR it.createdBy = :createdBy)")
    Page<InvitationToken> findInvitationsWithFilters(
        @Param("email") String email,
        @Param("used") Boolean used,
        @Param("createdBy") User createdBy,
        Pageable pageable
    );
    
    @Query("SELECT COUNT(it) FROM InvitationToken it WHERE it.used = :used")
    long countByUsed(@Param("used") Boolean used);
    
    @Modifying
    @Query("DELETE FROM InvitationToken it WHERE it.expiresAt < :now")
    void deleteExpired(@Param("now") LocalDateTime now);
    
    boolean existsByEmailAndUsedFalseAndExpiresAtAfter(String email, LocalDateTime now);
}