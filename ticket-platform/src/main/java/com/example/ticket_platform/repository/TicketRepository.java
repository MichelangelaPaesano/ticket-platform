package com.example.ticket_platform.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ticket_platform.model.Operator;
import com.example.ticket_platform.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer>{
        Optional<Ticket> findByOperator(Operator operator);

        List<Ticket> findAllByOperator(Operator operator);

}
