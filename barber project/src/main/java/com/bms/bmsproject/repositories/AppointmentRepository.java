package com.bms.bmsproject.repositories;

import com.bms.bmsproject.entities.Appointment;
import com.bms.bmsproject.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    default List<Appointment> findByBarber(User user) {
        return findByBarber(UserEntity.fromUser(user));
    }

    List<Appointment> findByBarber(UserEntity barber);

    default List<Appointment> findByCustomer(User user) {
        return findByCustomer(UserEntity.fromUser(user));
    }

    List<Appointment> findByCustomer(UserEntity customer);

    // List<Appointment> findByBarberId(UserEntity barber);

    // List<Appointment> findByCustomerId(UserEntity customer);

    // default List<Appointment> findByBarber(User user) {
    //     return findByBarberId(UserEntity.fromUser(user));
    // }

    // default List<Appointment> findByCustomer(User user) {
    //     return findByCustomerId(UserEntity.fromUser(user));
    // }

}