// RefreshTokenRepository.java - Place in: itss-auth-module/src/main/java/com/itss/auth/repository/
package com.itss.auth.repository;

import com.itss.auth.entity.RefreshToken;
import com.itss.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    
    Optional<RefreshToken> findByToken(String token);
    
    List<RefreshToken> findByUser(User user);
    
    List<RefreshToken> findByUserAndIsRevokedFalse(User user);
    
    @Modifying
    @Query("UPDATE RefreshToken rt SET rt.isRevoked = true WHERE rt.user = :user")
    void revokeAllByUser(@Param("user") User user);
    
    @Modifying
    @Query("UPDATE RefreshToken rt SET rt.isRevoked = true WHERE rt.token = :token")
    void revokeByToken(@Param("token") String token);
    
    @Modifying
    @Query("DELETE FROM RefreshToken rt WHERE rt.expiresAt < :now OR rt.isRevoked = true")
    void deleteExpiredAndRevoked(@Param("now") LocalDateTime now);
    
    @Query("SELECT COUNT(rt) FROM RefreshToken rt WHERE rt.user = :user AND rt.isRevoked = false AND rt.expiresAt > :now")
    long countActiveTokensByUser(@Param("user") User user, @Param("now") LocalDateTime now);
}