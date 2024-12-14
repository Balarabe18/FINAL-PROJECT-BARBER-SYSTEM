package com.bms.bmsproject.controllers;

import com.bms.bmsproject.entities.Appointment;
import com.bms.bmsproject.entities.UserEntity;
import com.bms.bmsproject.repositories.UserRepository;
import com.bms.bmsproject.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Optional;

import java.util.List;

@Controller
public class DashboardController {

    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;

    

    @Autowired
    public DashboardController(UserRepository userRepository, AppointmentRepository appointmentRepository) {
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @GetMapping("/dashboard")
    @Transactional
    public String showDashboard(@AuthenticationPrincipal User user, Model model) {
        try {
            boolean isCustomer = user.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_CUSTOMER"));
            boolean isBarber = user.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_BARBER"));
            boolean isAdmin = user.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

            // Add role-specific flags to the model
            model.addAttribute("isCustomer", isCustomer);
            model.addAttribute("isBarber", isBarber);
            model.addAttribute("isAdmin", isAdmin);

            // System.out.println("sexy Tont");
            // System.out.println(jokeService.getJokes()[0].getJoke()+" jokes");

            Optional<UserEntity> userEntityOptional = userRepository.findByUsername(user.getUsername());

            if (userEntityOptional.isPresent()) {
                UserEntity userEntity = userEntityOptional.get();
                UserEntity savedUser = userRepository.save(userEntity); // Save the UserEntity instance

                // Retrieve appointments based on user role
                if (isCustomer) {
                    List<Appointment> appointments = appointmentRepository.findByCustomer(savedUser);
                    model.addAttribute("appointments", appointments);
                } else if (isBarber) {
                    List<Appointment> appointments = appointmentRepository.findByBarber(savedUser);
                    model.addAttribute("appointments", appointments);
                }
                // ...
            } else {
                // Handle the case when user is not found
                // e.g., return an error message or redirect to login page
                return "error";
            }

            // Retrieve all barbers
            List<UserEntity> barbers = userRepository.findByRole(UserEntity.Role.BARBER);
            model.addAttribute("barbers", barbers);

            // // Retrieve all barbers

            // // Retrieve appointments based on user role
            

            return "dashboard";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

        // Get roles from the authenticated user

    }

}
