package com.example.ticket_platform.controller;

import java.security.Principal;
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

import com.example.ticket_platform.model.NotaForm;
import com.example.ticket_platform.model.Note;
import com.example.ticket_platform.model.Operator;
import com.example.ticket_platform.model.Ticket;
import com.example.ticket_platform.model.TicketStatus.ticketStatus;
import com.example.ticket_platform.model.User;
import com.example.ticket_platform.repository.NoteRepository;
import com.example.ticket_platform.repository.OperatorRepository;
import com.example.ticket_platform.repository.TicketRepository;
import com.example.ticket_platform.repository.userRepository;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/operator/tickets")
public class OperatorController {
    @Autowired
    private TicketRepository repository; 

    @Autowired
    private OperatorRepository opRepository;

    @Autowired
    private userRepository userRepo;

    @Autowired
    private NoteRepository noteRepository;

    @GetMapping("/")
    public String Tickets (Model model, Principal principal) {
        
        String username = principal.getName();
        Optional<User> userOpt = userRepo.findByUsername(username);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("Operatore non trovato");
        } 
        User user = userOpt.get();

        Optional<Operator> opOpt = opRepository.findByUser(user);
        if (!opOpt.isPresent()) {
            throw new RuntimeException("Operatore non trovato");
        }
        Operator operator = opOpt.get();

        List<Ticket> tickets = repository.findAllByOperatorId(operator.getId());
        System.out.println("Ticket per operatore " + operator.getName() + ": " + tickets.size());

        model.addAttribute("tickets", tickets);

        return "operator/tickets";
    }

    @GetMapping("/{id}")
    public String showTicket (@PathVariable Integer id, Model model, Principal principal)  {
        String username = principal.getName();
        Optional<User> userOpt = userRepo.findByUsername(username);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("Operatore non trovato");
        }
        User user = userOpt.get();

        Optional<Operator> opOpt = opRepository.findByUser(user);
        if (!opOpt.isPresent()) {
            throw new RuntimeException("Operatore non trovato");
        }
        Operator operator = opOpt.get();

        Optional<Ticket> ticketOpt = repository.findById(id);
        if (!ticketOpt.isPresent()) {
            throw new RuntimeException ("Operatore non trocato");
        }
        Ticket ticket = ticketOpt.get();

        if (!ticket.getOperator().getId().equals(operator.getId())) {
            return "error/403";
        }

        model.addAttribute("notaForm", new Note());
        model.addAttribute("ticket", ticket);
        
        return "operator/ticket_detail";
    }

   
    @PostMapping("/{id}/update")
    public String updateTicket(@PathVariable Integer id,
                               @Valid @ModelAttribute("notaForm") NotaForm notaForm,
                               BindingResult bindingResult,
                               Principal principal,
                               Model model) {

        String username = principal.getName();

        Optional<User> userOpt = userRepo.findByUsername(username);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("Utente non trovato");
        }
        User user = userOpt.get();

        Optional<Operator> opOpt = opRepository.findByUser(user);
        if (!opOpt.isPresent()) {
            throw new RuntimeException("Operatore non trovato");
        }
        Operator operator = opOpt.get();

        Optional<Ticket> ticketOpt = repository.findById(id);
        if (!ticketOpt.isPresent()) {
            throw new RuntimeException("Ticket non trovato");
        }
        Ticket ticket = ticketOpt.get();

        // Controllo sicurezza
        if (!ticket.getOperator().getId().equals(operator.getId())) {
            return "error/403";
        }

        // Gestione validazione
        if (bindingResult.hasErrors()) {
            model.addAttribute("ticket", ticket);
            return "operator/ticket_detail";
        }

        // Aggiorna lo stato del ticket usando l'enum
        if (notaForm.getStatus() != null && !notaForm.getStatus().isEmpty()) {
            ticket.setStatus(ticketStatus.valueOf(notaForm.getStatus().toUpperCase()));
            repository.save(ticket);
        }

        // Aggiunge una nuova nota se presente
        if (notaForm.getNoteText() != null && !notaForm.getNoteText().isEmpty()) {
            Note nota = new Note();
            nota.setTesto(notaForm.getNoteText());
            nota.setAutore(user.getUsername());
            nota.setTicket(ticket);
            noteRepository.save(nota);
        }

        return "redirect:/operator/tickets/" + id;
    }

    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal) {
        String username = principal.getName();

        Optional<User> userOpt = userRepo.findByUsername(username);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("Utente non trovato");
        }
        User user = userOpt.get();

        Optional<Operator> opOpt = opRepository.findByUser(user);
        if (!opOpt.isPresent()) {
            throw new RuntimeException("Operatore non trovato");
        }
        Operator operator = opOpt.get();

        model.addAttribute("operator", operator);
        return "operator/profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute("operator") Operator operatorForm, Principal principal, Model model) {
        String username = principal.getName();

        Optional<User> userOpt = userRepo.findByUsername(username);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("Utente non trovato");
        }
        User user = userOpt.get();

        Optional<Operator> opOpt = opRepository.findByUser(user);
        if (!opOpt.isPresent()) {
            throw new RuntimeException("Operatore non trovato");
        }
        Operator operator = opOpt.get();

        // Controllo: se vuole diventare "non disponibile"
        if (!operatorForm.isDisponibile()) {
            List<Ticket> tickets = repository.findByOperator(operator);
            for (Ticket t : tickets) {
                if (t.getStatus() == ticketStatus.DA_FARE || t.getStatus() == ticketStatus.IN_CORSO) {
                    model.addAttribute("operator", operator);
                    model.addAttribute("errorMessage", "Non puoi diventare non disponibile: hai ticket aperti.");
                    return "operator/profile";
                }
            }
        }

        // Aggiorna i dati dell'operatore
        operator.setName(operatorForm.getName()); 
        operator.setDisponibile(operatorForm.isDisponibile());
        opRepository.save(operator);

        return "redirect:/operator/profile";
    }






}
