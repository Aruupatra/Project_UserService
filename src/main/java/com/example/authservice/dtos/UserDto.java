package com.example.authservice.dtos;

import com.example.authservice.models.Role;
import com.example.authservice.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserDto {

    private String email;
    private List<Role> roles;

    public static UserDto from(User user)
    {
        UserDto userDto=new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        return userDto;

    }
}
