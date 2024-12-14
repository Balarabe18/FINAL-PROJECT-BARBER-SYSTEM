package com.bms.bmsproject.controllers;

import com.bms.bmsproject.entities.Appointment;
import com.bms.bmsproject.entities.UserEntity;
import com.bms.bmsproject.repositories.AppointmentRepository;
import com.bms.bmsproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    @Autowired
    public AppointmentController(AppointmentRepository appointmentRepository, UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
    }

    // @GetMapping("/dashboard")
    // public String showDashboard(@AuthenticationPrincipal User customer, Model
    // model) {
    // List<Appointment> appointments =
    // appointmentRepository.findByCustomer(customer);
    // model.addAttribute("appointments", appointments);
    // return "dashboard";
    // }

    @GetMapping("/dashboard/book-appointment")
    public String showBookAppointmentForm(@AuthenticationPrincipal UserEntity customer, Model model) {
        List<UserEntity> barbers = userRepository.findByRole(UserEntity.Role.BARBER);
        model.addAttribute("barbers", barbers);
        return "dashboard";
    }

    @PostMapping("/appointments/book")
    public String bookAppointment(@RequestParam Long barberId, @RequestParam LocalDateTime dateTime,
            @AuthenticationPrincipal User user, Model model, RedirectAttributes redirectAttributes) {
        try {
            UserEntity barber = userRepository.findById(barberId).orElseThrow();
            UserEntity customer = userRepository.findByUsername(user.getUsername()).orElseThrow();

            Appointment appointment = new Appointment();
            appointment.setBarber(barber);
            appointment.setCustomer(customer);
            appointment.setDateTime(dateTime);
            appointment.setStatus("pending");
            appointmentRepository.save(appointment);
            redirectAttributes.addFlashAttribute("success", "Appointment booked successfully!");
            return "redirect:/dashboard?section=book-appointment";
        } catch (Exception e) {

            redirectAttributes.addFlashAttribute("error", "Error Occured !" + e.getMessage());
            return "redirect:/dashboard?section=book-appointment";
        }
    }

    @PostMapping("/appointments/reschedule")
    public String rescheduleAppointment(@RequestParam Long appointmentId, @RequestParam LocalDateTime dateTime,
            RedirectAttributes redirectAttributes) {
        try {
            Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow();
            if (appointment.getStatus().equals("completed")) {
                redirectAttributes.addFlashAttribute("error", "Cannot reschedule a completed appointment!");
                return "redirect:/dashboard?section=appointments";
            }
            appointment.setDateTime(dateTime);
            appointmentRepository.save(appointment);
            redirectAttributes.addFlashAttribute("success", "Appointment rescheduled successfully!");
            return "redirect:/dashboard?section=appointments";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error Occurred: " + e.getMessage());
            return "redirect:/dashboard?section=appointments";
        }
    }

    @PostMapping("/appointments/cancel")
    public String cancelAppointment(@RequestParam Long appointmentId, RedirectAttributes redirectAttributes) {
        // Update the appointment status to "cancelled"
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow();
        appointment.setStatus("cancelled");
        appointmentRepository.save(appointment);
        redirectAttributes.addFlashAttribute("success", "Appointment cancelled successfully!");
        return "redirect:/dashboard?section=appointments";
    }

    @PostMapping("/appointments/confirm")
    public String confirmAppointment(@RequestParam Long appointmentId, RedirectAttributes redirectAttributes) {
        // Update the appointment status to "completed"
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow();
        appointment.setStatus("completed");
        appointmentRepository.save(appointment);
        redirectAttributes.addFlashAttribute("success", "Appointment confirmed successfully!");
        return "redirect:/dashboard?section=appointments";
    }

}
