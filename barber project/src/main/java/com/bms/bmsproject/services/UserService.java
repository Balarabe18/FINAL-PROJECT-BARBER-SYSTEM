package com.bms.bmsproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bms.bmsproject.repositories.UserRepository;  // Assuming this works with entities.User
import com.bms.bmsproject.entities.UserEntity;  // Correct import for JPA entity User

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;  // Your JPA repository for User entity

    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }
}
