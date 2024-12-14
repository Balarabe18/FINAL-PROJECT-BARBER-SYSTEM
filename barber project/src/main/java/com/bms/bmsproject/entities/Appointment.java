package com.bms.bmsproject.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "barber_id", nullable = false)
    private UserEntity barber;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private UserEntity customer;

    // Getters
    public Long getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getStatus() {
        return status;
    }

    public UserEntity getBarber() {
        return barber;
    }

    public UserEntity getCustomer() {
        return customer;
    }

  

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setBarber(UserEntity barber) {
        this.barber = barber;
    }

    public void setCustomer(UserEntity customer) {
        this.customer = customer;
    }
}
