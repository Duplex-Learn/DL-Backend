package com.duplexlearn.controller;

import com.duplexlearn.model.LoginForm;
import com.duplexlearn.model.JwtToken;
import com.duplexlearn.service.JwtUserDetailsService;
import com.duplexlearn.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * The authentication controller.
 * <p>
 * Including user login operations.
 *
 * @author LoveLonelyTime
 */
@RestController
@RequestMapping("/auth")
public class JwtAuthenticationController {

    private AuthenticationManager authenticationManager;
    private JwtUserDetailsService jwtUserDetailsService;
    private JwtUtil jwtUtil;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setJwtUserDetailsService(JwtUserDetailsService jwtUserDetailsService) {
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @Autowired
    public void setJwtUtil(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * Process user login.
     *
     * @param loginForm Email and password
     * @return jwt
     */
    @PostMapping("/token")
    public JwtToken createAuthenticationToken(@RequestBody LoginForm loginForm) {
        authenticate(loginForm.getEmail(), loginForm.getPassword());
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(loginForm.getEmail());
        String token = jwtUtil.generateToken(userDetails);
        return new JwtToken(token);
    }

    /**
     * Authenticate the username and password by authenticationManager.
     *
     * @param username User's username
     * @param password User's password
     */
    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}

