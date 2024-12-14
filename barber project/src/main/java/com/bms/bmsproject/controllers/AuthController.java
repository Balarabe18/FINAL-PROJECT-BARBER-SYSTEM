package com.bms.bmsproject.controllers;

import com.bms.bmsproject.entities.UserEntity;
import com.bms.bmsproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Login page
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // Login page (Spring Security handles the rest)
    }

    // Registration page
    @GetMapping("/register")
    public String registerPage() {
        return "register"; // Registration page
    }

    // Registration process
    @PostMapping("/register")
    public String registerUser(
            UserEntity user,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model,
            RedirectAttributes redirectAttributes) {
        // Check if passwords match
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            return "register"; // Return to the registration page with the error message
        }

        // Check if the username or email already exists (optional)
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("error", "Username is already taken.");
            return "register"; // Return to the registration page with the error message
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("error", "Email is already in use.");
            return "register"; // Return to the registration page with the error message
        }

        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set default role to CUSTOMER
        user.setRole(UserEntity.Role.CUSTOMER);

        // Save the user to the database
        userRepository.save(user);

        // Add a success message as a flash attribute
        redirectAttributes.addFlashAttribute("message", "Registration successful! Please log in.");

        // Redirect to the login page
        return "redirect:/login";
    }

}
