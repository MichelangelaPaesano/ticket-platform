package com.example.ticket_platform.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ticket_platform.model.Ticket;
import com.example.ticket_platform.model.TicketStatus.ticketStatus;
import com.example.ticket_platform.repository.TicketRepository;

@RestController
@CrossOrigin
@RequestMapping ("api/tickets")
public class TicketController {

    @Autowired
    private TicketRepository repository;

    @GetMapping("/")
    public List<Ticket> TicketList() {
        return repository.findAll();
    }
        
    @GetMapping("/categoria")
    public List<Ticket> TicketCategory(@RequestParam(name = "categoria", required = false) String categoria) {
        List<Ticket> result = null; 
        if (categoria !=null && categoria.equals("errore")) {
            throw new IllegalArgumentException("Parametro errato");
        } else if (categoria != null && !categoria.isBlank()) {
            result = repository.findByCategoria_NameContainingIgnoreCase(categoria);
        } else {
            result = repository.findAll();
        }
        return result;
    }

    @GetMapping("/status")
    public List<Ticket> TicketStatus(@RequestParam (name = "status", required = false) String status) {
        List<Ticket> result = null;
        if (status !=null && status.equals("errore")) {
            throw new IllegalArgumentException("Parametro errato");
        } else if (status != null && !status.isBlank()) {
            ticketStatus enumStatus = ticketStatus.valueOf(status.toUpperCase());
            result = repository.findByStatus(enumStatus); 
        } else {
            result = repository.findAll();
        }
        return result;
    }
    
    

}
