package com.example.ticket_platform.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotNull
    @NotBlank
    private String name;

    @OneToMany(mappedBy = "categoria")
    private List<Ticket> tickets; 

    public Integer getId() {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }
    
    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public List<Ticket> getTickets () {
        return tickets;
    }

    public void setTickets (List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
