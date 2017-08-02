package com.blibli.future.detroit.service;

import com.blibli.future.detroit.model.User;
import com.blibli.future.detroit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;

    public boolean isLoggedIn() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken) ;
    }

    public User getCurrentUser() {
        if (isLoggedIn()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return userRepository.findByEmail(auth.getName());
        }
        return null;
    }
}
