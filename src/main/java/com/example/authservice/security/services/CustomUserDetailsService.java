package com.example.authservice.security.services;

import com.example.authservice.models.User;
import com.example.authservice.repositories.UserRepository;
import com.example.authservice.security.models.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository)
    {
        this.userRepository=userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user=userRepository.findUserByEmail(username);

        if(user.isEmpty())
        {
            throw new UsernameNotFoundException("User Not exit "+username+" on the email");
        }

      return new CustomUserDetails(user.get());
    }
}
