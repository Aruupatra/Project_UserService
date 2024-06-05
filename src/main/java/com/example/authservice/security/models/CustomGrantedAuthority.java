package com.example.authservice.security.models;

import com.example.authservice.models.Role;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@JsonSerialize
@NoArgsConstructor
public class CustomGrantedAuthority implements GrantedAuthority {

//wrong
//    private Role role;
//
//    public CustomGrantAuthority(Role role)
//    {
//        this.role=role;
//    }
//    @Override
//    public String getAuthority() {
//        return role.getRole();
//    }

    private String authority;

    public CustomGrantedAuthority(Role role) {
        this.authority = role.getRole();
    }


    @Override
    public String getAuthority() {
        return this.authority;
    }
}
