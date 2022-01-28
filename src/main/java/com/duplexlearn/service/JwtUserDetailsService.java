package com.duplexlearn.service;

import com.duplexlearn.dao.UserDao;
import com.duplexlearn.model.User;
import org.springframework.beans.factory.annotation.Autowired;
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

    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Locate the user based on the username
        User user = userDao.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Collections.emptyList());
    }
}
