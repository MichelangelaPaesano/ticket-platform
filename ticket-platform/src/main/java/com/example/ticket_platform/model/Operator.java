package com.example.ticket_platform.model;

import java.util.List;

import com.example.ticket_platform.model.TicketStatus.ticketStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Operator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NotBlank
    private String name;

    
    private boolean disponibile = true;

    @OneToMany(mappedBy = "operator")
    @JsonIgnore
    private List<Ticket> tickets;

    public Integer getId () {
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

    public Boolean isDisponibile () {
        return disponibile;
    }

    public void setDisponibile (boolean disponibile) {
        this.disponibile = disponibile;
    }

    public List<Ticket> getTickets () {
        return tickets;
    }

    public void setTickets (List<Ticket> tickets) {
        this.tickets = tickets;
    }

    //scrivo un metodo che mi permetta di rendere dinamico il flag per operatore
    public boolean disponibilita () {
        for (Ticket t : tickets) {
            if (t.getStatus() == ticketStatus.DA_FARE || t.getStatus() == ticketStatus.IN_CORSO) {
                disponibile = false;
                return false;
            }
        } disponibile = true;
        return true;
    }
}
