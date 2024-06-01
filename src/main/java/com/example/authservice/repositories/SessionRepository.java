package com.example.authservice.repositories;

import com.example.authservice.models.Session;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Id> {


    public Session save(Session session);
    public Optional<Session> findByTokensAndUser_Id(String token,Long userId);

}
