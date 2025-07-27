package com.itss.auth.repository;

import com.itss.auth.entity.Invite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InviteRepository extends JpaRepository<Invite, Long> {
    Optional<Invite> findByToken(String token);
    Optional<Invite> findByEmailAndStatus(String email, Invite.Status status);
} 
