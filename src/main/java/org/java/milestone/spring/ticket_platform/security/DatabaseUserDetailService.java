package org.java.milestone.spring.ticket_platform.security;

import java.util.Optional;

import org.java.milestone.spring.ticket_platform.model.User;
import org.java.milestone.spring.ticket_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class DatabaseUserDetailService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userAttempt = userRepository.findByUsername(username);
        if (userAttempt.isEmpty()) {
            throw new UsernameNotFoundException("username non trovato");
        }
        return new DatabaseUserDetails(userAttempt.get());
    }
}
