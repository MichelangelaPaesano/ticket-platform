package com.example.ticket_platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ticket_platform.model.Operator;
import com.example.ticket_platform.model.Ticket;
import com.example.ticket_platform.model.TicketStatus.ticketStatus;

public interface TicketRepository extends JpaRepository<Ticket, Integer>{
        List<Ticket> findByOperator(Operator operator);

        List<Ticket> findAllByOperatorId(Integer operator);

        public List<Ticket> findByTitleContainingIgnoreCase(String title);

        public List<Ticket> findByCategoria_NameContainingIgnoreCase(String name);

        public List<Ticket> findByStatus(ticketStatus status);
}
