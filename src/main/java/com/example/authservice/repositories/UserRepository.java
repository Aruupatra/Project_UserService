package com.example.authservice.repositories;

import com.example.authservice.models.User;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Id> {

    public User save(User user);
    Optional<User> findByEmail(String email);

    public User findById(Long id);

    Optional<User> findUserByEmail(String email);
}
