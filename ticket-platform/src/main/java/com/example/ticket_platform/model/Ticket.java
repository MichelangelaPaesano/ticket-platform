package com.example.ticket_platform.model;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.ticket_platform.model.TicketStatus.ticketStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message="Il titolo è obbligatorio")
    private String title;

    @NotBlank(message="La descrizione è obbligatoria")
    private String description;

    @Column(nullable = false, updatable = false)
    private LocalDate creationDate;
    
    @NotNull(message="Lo stato è obbligatorio")
    @Enumerated(EnumType.STRING)
    private ticketStatus status;
   
    
    
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    @NotNull
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "operator_id", nullable = false)
    @NotNull
    private Operator operator;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Note> note = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public String getDescription () {
        return description;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public LocalDate getCreationDate () {
        return creationDate;
    }

    public void setCreationDate (LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public ticketStatus getStatus () {
        return status;
    }

    public void setStatus (ticketStatus status) {
        this.status = status;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria (Categoria categoria) {
        this.categoria = categoria;
    }

    public Operator getOperator () {
        return operator;
    }

    public void setOperator (Operator operator) {
        this.operator = operator;
    }

    public List<Note> getNote () {
        return note;
    }

    public void setNote (List<Note> note) {
        this.note = note;
    }


}
