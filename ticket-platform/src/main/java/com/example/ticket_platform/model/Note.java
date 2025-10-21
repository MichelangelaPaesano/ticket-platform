package com.example.ticket_platform.model;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NotBlank
    private String autore;

    private String testo;

    @CreationTimestamp
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    //getter e setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore (String autore) {
        this.autore = autore;
    }

    public String getTesto () {
        return testo;
    }

    public void setTesto (String testo) {
        this.testo = testo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData (LocalDate data) {
        this.data = data;
    }

    public Ticket getTicket () {
        return ticket;
    }
 
    public void setTicket (Ticket ticket) {
        this.ticket = ticket;
    }
}
