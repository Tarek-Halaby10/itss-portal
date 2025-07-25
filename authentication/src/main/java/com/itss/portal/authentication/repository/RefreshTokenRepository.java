package com.itss.portal.authentication.repository;

import com.itss.portal.authentication.model.RefreshToken;
import com.itss.portal.authentication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
    // ← add this line:
    void deleteByToken(String token);
}
