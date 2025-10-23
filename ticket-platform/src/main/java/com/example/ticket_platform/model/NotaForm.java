package com.example.ticket_platform.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class NotaForm {

    @Size(max=500)
    private String noteText;   

    @NotNull (message="Lo status non può essere vuoto")
    private String status;  
       
    // getter e setter
    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}



