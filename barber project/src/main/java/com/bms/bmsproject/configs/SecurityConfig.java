package com.bms.bmsproject.configs;

import com.bms.bmsproject.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Injecting CustomUserDetailsService for user authentication
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http
                .getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers("/login", "/register", "/error", "/", "/hair-styles", "/hair-products").permitAll() // Allow
                                                                                                                     // unauthenticated
                                                                                                                     // access
                                                                                                                     // to
                .requestMatchers("/css/**", "/js/**", "/images/**","/**").permitAll()
                // login, register, and error pages
                .requestMatchers("/admin/**").hasRole("ADMIN") // Allow only admins
                .requestMatchers("/customer/appointments/book").hasRole("CUSTOMER") // Allow only customers
                .requestMatchers("/customer/appointments/confirm").hasRole("BARBER") // Allow only customers
                .requestMatchers("/customer/appointments/cancel").hasRole("CUSTOMER") // Allow only customers
                .requestMatchers("/customer/appointments/reschedule").hasAnyRole("CUSTOMER", "BARBER") // Allow only
                                                                                                       // customers and
                                                                                                       // barbers
                .anyRequest().authenticated() // Secure all other requests
                .and()
                .formLogin()
                .loginPage("/login") // Custom login page
                .loginProcessingUrl("/login") // URL where the login form submits
                .defaultSuccessUrl("/dashboard", true)
                .failureUrl("/login?error=true") // Redirect to login page with error message on failure
                .and()
                .logout()
                .logoutUrl("/logout") // Logout URL
                .logoutSuccessUrl("/login") // Redirect to login page after logout
                .and()
                .exceptionHandling()
                .accessDeniedPage("/error") // Custom error page for access denied
                .and()
                .csrf().disable(); // Disable CSRF for simplicity (not recommended for production)

        return http.build();
    }

    // Password encoder bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Password encoding method
    }
}
