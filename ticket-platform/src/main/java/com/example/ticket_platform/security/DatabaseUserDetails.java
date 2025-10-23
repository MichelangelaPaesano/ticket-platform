package com.example.ticket_platform.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.ticket_platform.model.Role;
import com.example.ticket_platform.model.User;

public class DatabaseUserDetails implements UserDetails {
    private String username;
    
    private String password;

    private Set<GrantedAuthority> authorities;

    public DatabaseUserDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.authorities = new HashSet();
        for (Role role : user.getRole()) {
            SimpleGrantedAuthority sGA = new SimpleGrantedAuthority("ROLE_" + role.getName());
            this.authorities.add(sGA); 
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

}
