package com.example.authservice.security.models;

import com.example.authservice.models.Role;
import com.example.authservice.models.User;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@JsonSerialize
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {


   // private User user;

    private List<CustomGrantedAuthority> authorities;
    private String password;
    private String username;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;



    private Long userId;


    public CustomUserDetails(User user)
    {


        authorities=new ArrayList<>();
        for(Role role: user.getRoles())
        {
            authorities.add(new CustomGrantedAuthority(role));
        }
       this.password=user.getPassword();
        this.username=user.getEmail();
        this.accountNonExpired=true;
        this.accountNonLocked=true;
        this.credentialsNonExpired=true;
        this.enabled=true;
        this.userId=user.getId();
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
       this.userId=userId;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

//        List<CustomGrantAuthority> customGrantAuthorityList=new ArrayList<>();
//
//        for(Role role: user.getRoles())
//        {
//            customGrantAuthorityList.add(new CustomGrantAuthority(role));
//        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
