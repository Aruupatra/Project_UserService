package com.example.authservice.controlers;

import com.example.authservice.dtos.creatRoleRequestDto;
import com.example.authservice.models.Role;
import com.example.authservice.services.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleControler {


    private RoleService roleService;
    public RoleControler(RoleService roleService)
    {
        this.roleService=roleService;
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody  creatRoleRequestDto dto)
    {
        Role role=roleService.addNewRole(dto.getName());

        return new ResponseEntity<>(role, HttpStatus.CREATED);
    }



}
