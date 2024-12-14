package com.bms.bmsproject.repositories;

import com.bms.bmsproject.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // Method to find a user by username
    Optional<UserEntity> findByUsername(String username);

    // Method to find a user by email
    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findByRole(UserEntity.Role role);

    boolean existsByUsernameOrEmail(String username, String email);

    // You can also add more query methods if needed, for example:
    // Optional<User> findByRole(User.Role role);
}
