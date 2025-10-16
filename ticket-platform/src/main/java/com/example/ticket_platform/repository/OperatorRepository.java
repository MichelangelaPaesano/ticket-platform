package com.example.ticket_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ticket_platform.model.Operator;

public interface OperatorRepository extends JpaRepository<Operator, Integer> {

}
