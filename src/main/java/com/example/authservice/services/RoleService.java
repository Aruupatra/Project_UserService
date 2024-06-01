package com.example.authservice.services;

import com.example.authservice.models.Role;
import com.example.authservice.repositories.RoleRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService  {

    private RoleRepository roleRepository;
    public RoleService(RoleRepository roleRepository)
    {
        this.roleRepository=roleRepository;
    }

    public Role addNewRole(String name)
    {
        Role role=new Role();
        role.setRole(name);
        Role role1=roleRepository.save(role);
        return role1;

    }
}
