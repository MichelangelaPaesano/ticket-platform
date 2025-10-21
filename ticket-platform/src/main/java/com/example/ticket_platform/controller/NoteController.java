package com.example.ticket_platform.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ticket_platform.model.Note;
import com.example.ticket_platform.model.Ticket;
import com.example.ticket_platform.repository.NoteRepository;
import com.example.ticket_platform.repository.TicketRepository;

import jakarta.validation.Valid;



@Controller
@RequestMapping("/admin/tickets/note")

public class NoteController {

    @Autowired
    private TicketRepository repository;
    
    @Autowired 
    private NoteRepository noteRepository;

        
    //Questo Get Mapping serve per la lettura della lista delle note 
    @GetMapping("/{ticketId}")
    public String index (@PathVariable("ticketId") Integer id, Model model) {
        Optional<Ticket> optionalTicket = repository.findById(id);
        if (optionalTicket.isPresent()) {
            Ticket ticket = optionalTicket.get();
        
        // Recupera le note associate a quel ticket
        List<Note> noteList = noteRepository.findByTicketId(id);
        
        // Aggiungi ticket e lista note al model
        model.addAttribute("ticket", ticket);
        model.addAttribute("noteList", noteList);
        model.addAttribute("nota", new Note());
        // Ritorna la view show.html
        return "admin/show";
        } else {
        // Ticket non trovato → redirect alla lista dei ticket
        return "redirect:/admin/tickets";
        }
    }

    //post per creare una nuova nota
    @PostMapping("/{ticketId}")
    public String addNota (@PathVariable("ticketId") Integer id, @Valid @ModelAttribute("nota") Note nota,
                            BindingResult bindingResult, Model model) {
        // Controlla errori di validazione
    if (bindingResult.hasErrors()) {
        Optional<Ticket> optionalTicket = repository.findById(id);
        if (optionalTicket.isPresent()) {
            Ticket ticket = optionalTicket.get();
            model.addAttribute("ticket", ticket);
            model.addAttribute("noteList", noteRepository.findByTicketId(id));
        }
        return "admin/show"; // ritorna alla pagina con errori
    }

    // Recupera il ticket
    Optional<Ticket> optionalTicket = repository.findById(id);
    if (optionalTicket.isEmpty()) {
        return "redirect:/admin/tickets"; // ticket non trovato
    }

    Ticket ticket = optionalTicket.get();

    // Imposta ticket nota
    nota.setTicket(ticket);


    // Salva la nota
    noteRepository.save(nota);

    // Redirect alla stessa pagina per vedere subito la nuova nota
    return "redirect:/admin/tickets/show/" + id;
    }
    
}