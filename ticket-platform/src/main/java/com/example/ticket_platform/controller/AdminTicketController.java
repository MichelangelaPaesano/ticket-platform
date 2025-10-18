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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.ticket_platform.model.Ticket;
import com.example.ticket_platform.repository.CategoriaRepository;
import com.example.ticket_platform.repository.OperatorRepository;
import com.example.ticket_platform.repository.TicketRepository;

import jakarta.validation.Valid;





@Controller
@RequestMapping("/admin/tickets")

public class AdminTicketController {

    @Autowired
    private TicketRepository repository;
    //per adesso inietto tolo ticket repository perchè credo che debba lavorare solo su quella per ora!!!

    @Autowired
    private CategoriaRepository catRepository;

    @Autowired
    private OperatorRepository opRepository;
    
    
    //Questo Get Mapping serve per la lettura della lista dei ticket 
    @GetMapping("/")
    public String index (Model model) {
        List<Ticket> mostraLista = repository.findAll();
        model.addAttribute("ticket", mostraLista);
        return "admin/tickets";
    }
    
    //questo show è per vedere un singolo ticket!!! 
    @GetMapping("/show/{id}")
    public String show (@PathVariable("id") Integer id, Model model) {
        Optional<Ticket> optionalTicket = repository.findById(id);
        if (optionalTicket.isPresent()) {
            model.addAttribute("ticket", optionalTicket.get());
            return "admin/show";
        } else {
            return "redirect:/admin/tickets"; 
        }

    }

    //Adesso facciamo get e post per la creazione di un nuovo ticket 
    @GetMapping("/create")
    public String create (Model model) {
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("categorie", catRepository.findAll());
        model.addAttribute("operatori", opRepository.findAll());
        return "admin/create";
    }

    @PostMapping("/create")
    public String save (@Valid @ModelAttribute("ticket") Ticket formTicket, 
                        BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        //controlliamo con binding result se ci sono errori 
        if (bindingResult.hasErrors()) {
            return "admin/create";
        }
        repository.save(formTicket);
        //e mostriamo un messaggio di salvataggio
        redirectAttributes.addFlashAttribute("successMessage", "Ticket creato con successo");
        return "redirect:/admin/tickets";
    }


    //Adesso facciamo get e post per editare e aggiornare!!!
    @GetMapping("/edit/{id}")
    public String edit (@PathVariable("id") Integer id, Model model) {
        Optional<Ticket> optTicket = repository.findById(id);
        if (optTicket.isPresent()) {
        Ticket ticket = optTicket.get();
        model.addAttribute("ticket", ticket);
        model.addAttribute("categorie", catRepository.findAll());
        model.addAttribute("operatori", opRepository.findAll());
        return "admin/edit";
        } else {
            return "redirect:/admin/tickets";
        }
    }

    @PostMapping("/edit/{id}")
    public String update (@PathVariable("id") Integer id, @Valid @ModelAttribute ("ticket") Ticket formTicket,
                        BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "admin/create";
        }
        //ci assicuriamo che l'id esista!
        Optional<Ticket> optTicket = repository.findById(id);
        if (optTicket.isPresent()) {
            repository.save(formTicket);
            redirectAttributes.addFlashAttribute("successMessage", "Ticket modificato con successo");  
        }
        return "redirect:/admin/tickets";
        
    }

    //post mapping per cancellazione 
       //metodo per delete 
    @PostMapping("/delete/{id}")
    public String delete (@PathVariable("id") Integer id) {
        repository.deleteById(id);
        
        return "redirect:/";
    }



    
    
    
    
    
}
