package com.example.authservice.services;

import com.example.authservice.dtos.UserDto;
import com.example.authservice.models.Role;
import com.example.authservice.models.User;
import com.example.authservice.repositories.RoleRepository;
import com.example.authservice.repositories.UserRepository;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
        private UserRepository userRepository;
    private RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public UserDto getUserDetails(Long userId) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findById(userId));

        if (userOptional.isEmpty()) {
            return null;
        }

        return UserDto.from(userOptional.get());
    }

    public UserDto setUserRoles(Long userId, List<Long> roleIds) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findById(userId));
        List<Role> roles = roleRepository.findAllByIdIn(roleIds);

        if (userOptional.isEmpty()) {
            return null;
        }

        User user = userOptional.get();
////        user.setRoles(Set.copyOf(roles));
//        user.getRoles().add()

        List<Role> rolesList=user.getRoles();
        for(Role role:roles)
        {
           rolesList.add(role);

        }

        User savedUser = userRepository.save(user);

        return UserDto.from(savedUser);
    }
}
