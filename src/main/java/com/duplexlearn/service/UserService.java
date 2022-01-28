package com.duplexlearn.service;

import com.duplexlearn.model.User;

/**
 * UserService.
 * <p>
 * Provide user logic services.
 *
 * @author LoveLonelyTime
 */
public interface UserService {
    /**
     * Pre registration.
     *
     * @param email email
     */
    void preRegister(String email);

    /**
     * Register.
     *
     * @param email    email
     * @param password password
     * @param uuid     puser's uuid
     * @return user
     */
    User register(String email, String password, String uuid);
}
