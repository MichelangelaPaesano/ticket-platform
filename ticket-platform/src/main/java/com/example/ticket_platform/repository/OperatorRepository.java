package com.example.ticket_platform.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ticket_platform.model.Operator;
import com.example.ticket_platform.model.User;

public interface OperatorRepository extends JpaRepository<Operator, Integer> {

    //creiamo una query custom per vedere gli operatori disponibili 
    public List<Operator> findByDisponibileTrue();

    Optional<Operator> findByUser(User user);
}
