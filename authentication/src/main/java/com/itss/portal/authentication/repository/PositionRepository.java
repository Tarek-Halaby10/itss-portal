package com.itss.portal.authentication.repository;

import com.itss.portal.authentication.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Long> {
    Optional<Position> findByName(String name);
}
