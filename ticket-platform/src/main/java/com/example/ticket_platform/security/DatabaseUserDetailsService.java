package com.example.ticket_platform.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.ticket_platform.model.User;
import com.example.ticket_platform.repository.userRepository;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {
    
    @Autowired
    private userRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOpt = userRepo.findByUsername(username);
        if (userOpt.isPresent()) {
            return new DatabaseUserDetails(userOpt.get());
        } else {
            throw new UsernameNotFoundException("Username non trovato");
        }
    }

}
