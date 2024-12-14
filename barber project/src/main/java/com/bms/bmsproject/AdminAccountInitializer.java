package com.bms.bmsproject;

import com.bms.bmsproject.entities.UserEntity;
import com.bms.bmsproject.entities.UserEntity.Role;
import com.bms.bmsproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminAccountInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        String defaultAdminUsername = "defaultAdmin";
        String defaultAdminEmail = "admin@example.com";
        String defaultAdminPassword = "password";

        // Check if an admin account already exists
        if (userRepository.findByUsername(defaultAdminUsername).isEmpty()) {
            // Create default admin account
            UserEntity admin = new UserEntity();
            admin.setUsername(defaultAdminUsername);
            admin.setEmail(defaultAdminEmail);
            admin.setPassword(passwordEncoder.encode(defaultAdminPassword));
            admin.setRole(Role.ADMIN);

            userRepository.save(admin);
            System.out.println("Default admin account created.");
        } else {
            System.out.println("Admin account already exists.");
        }
    }
}
