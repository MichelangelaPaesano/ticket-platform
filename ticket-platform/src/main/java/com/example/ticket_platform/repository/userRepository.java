package com.example.ticket_platform.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ticket_platform.model.User;

public interface userRepository extends JpaRepository<User, Integer>  {
    public Optional<User> findByUsername(String username);

}
