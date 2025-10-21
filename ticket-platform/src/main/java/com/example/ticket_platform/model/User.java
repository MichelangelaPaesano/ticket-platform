package com.example.ticket_platform.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;

@Entity
public class User {
    @Id
    private Integer id;
    
    @NotNull
    @Column(unique=true)
    private String username;

    @NotNull
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    public Integer getId () {
        return id;
    }

    public void setId (Integer id)  {
        this.id = id;
    }

    public String getUsername () {
        return username;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public List<Role> getRole () {
        return roles;
    }

    public void setRole (List<Role> roles) {
        this. roles = roles;
    }
    

}
