package com.example.gps.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.gps.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}