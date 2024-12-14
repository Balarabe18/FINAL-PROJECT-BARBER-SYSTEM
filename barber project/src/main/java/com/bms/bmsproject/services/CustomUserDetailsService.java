package com.bms.bmsproject.services;

import com.bms.bmsproject.entities.UserEntity;
import com.bms.bmsproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;  // Inject the UserRepository

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch the user from the database by username
        UserEntity user = userRepository.findByUsername(username)
                                  .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Convert the User entity to a Spring Security UserDetails object
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))  // Assuming roles are enums
        );
    }
}

