package com.duplexlearn.service;

import com.duplexlearn.model.LoginFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class JwtAuthenticationServiceImpl implements JwtAuthenticationService{
    private AuthenticationManager authenticationManager;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * Authenticate the username and password by authenticationManager.
     *
     * @param username User's username
     * @param password User's password
     */
    @Override
    public void authenticate(LoginFormDTO loginFormDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginFormDTO.getEmail(), loginFormDTO.getPassword()));
    }
}
