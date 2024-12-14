package com.bms.bmsproject.controllers;

import com.bms.bmsproject.entities.UserEntity;
import com.bms.bmsproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class BarberController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public BarberController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/barbers/add")
    public String addBarber(@ModelAttribute("barber") UserEntity newBarber, RedirectAttributes redirectAttributes,
            @RequestParam("section") String section) {
        try {
            // Check if the username or email already exists
            if (userRepository.existsByUsernameOrEmail(newBarber.getUsername(), newBarber.getEmail())) {
                redirectAttributes.addFlashAttribute("error", "Username or email already exists.");
                return "redirect:/dashboard?section=" + section;
            }
            // Encode password
            newBarber.setPassword(passwordEncoder.encode(newBarber.getPassword()));
            // Set role to BARBER
            newBarber.setRole(UserEntity.Role.BARBER);

            // Save the barber to the database
            userRepository.save(newBarber);

            // Add a success message as a flash attribute
            redirectAttributes.addFlashAttribute("success", "Barber added successfully!");
            return "redirect:/dashboard?section=" + section;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to add barber: " + e.getMessage());
            return "redirect:/dashboard?section=" + section;

        }
    }
}
