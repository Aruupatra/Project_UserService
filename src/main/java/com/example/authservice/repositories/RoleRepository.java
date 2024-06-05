package com.example.authservice.repositories;

import com.example.authservice.models.Role;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Id> {

    public Role save(Role role);

    List<Role> findAllByIdIn(List<Long> roleIds);
}
