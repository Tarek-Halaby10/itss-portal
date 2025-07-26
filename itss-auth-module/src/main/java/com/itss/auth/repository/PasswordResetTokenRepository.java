// PasswordResetTokenRepository.java - Place in: itss-auth-module/src/main/java/com/itss/auth/repository/
package com.itss.auth.repository;

import com.itss.auth.entity.PasswordResetToken;
import com.itss.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    
    Optional<PasswordResetToken> findByToken(String token);
    
    @Query("SELECT prt FROM PasswordResetToken prt WHERE prt.token = :token AND prt.used = false AND prt.expiresAt > :now")
    Optional<PasswordResetToken> findValidToken(@Param("token") String token, @Param("now") LocalDateTime now);
    
    @Modifying
    @Query("UPDATE PasswordResetToken prt SET prt.used = true WHERE prt.user = :user AND prt.used = false")
    void markAllAsUsedByUser(@Param("user") User user);
    
    @Modifying
    @Query("DELETE FROM PasswordResetToken prt WHERE prt.expiresAt < :now OR prt.used = true")
    void deleteExpiredAndUsed(@Param("now") LocalDateTime now);
}