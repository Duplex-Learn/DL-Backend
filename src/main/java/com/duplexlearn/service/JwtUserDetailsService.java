package com.duplexlearn.service;

import com.duplexlearn.dao.UserDAO;
import com.duplexlearn.model.LoginFormDTO;
import com.duplexlearn.model.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;


/**
 * The JwtUser DetailsService.
 * <p>
 * Defines how to find users from the database.
 *
 * @author LoveLonelyTime
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private UserDAO userDAO;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Locate the user based on the username
        UserPO user = userDAO.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Collections.emptyList());
    }
}
