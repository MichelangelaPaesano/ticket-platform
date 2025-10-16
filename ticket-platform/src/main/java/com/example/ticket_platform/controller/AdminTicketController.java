package com.example.ticket_platform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ticket_platform.model.Ticket;
import com.example.ticket_platform.repository.TicketRepository;


@Controller
@RequestMapping("/admin/tickets")

public class AdminTicketController {

    @Autowired
    private TicketRepository repository;
    //per adesso inietto tolo ticket repository perchè credo che debba lavorare solo su quella per ora!!!

    @GetMapping("/")
    public String index (Model model) {
        List<Ticket> mostraLista = repository.findAll();
        model.addAttribute("tickets", mostraLista);
        return "admin/ticket";
    }
    
    
}
