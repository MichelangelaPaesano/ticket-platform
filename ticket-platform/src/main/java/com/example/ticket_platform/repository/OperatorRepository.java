package com.example.ticket_platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ticket_platform.model.Operator;

public interface OperatorRepository extends JpaRepository<Operator, Integer> {

    //creiamo una query custom per vedere gli operatori disponibili 
    public List<Operator> findByDisponibileTrue();

}
