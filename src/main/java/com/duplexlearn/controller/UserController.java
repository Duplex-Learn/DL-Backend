package com.duplexlearn.controller;

import com.duplexlearn.model.PreRegisterForm;
import com.duplexlearn.model.RegisterForm;
import com.duplexlearn.model.User;
import com.duplexlearn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * User center.
 * <p>
 * Including user pre registration and registration.
 *
 * @author LoveLonelyTime
 */
@RestController
@RequestMapping("/usersys")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Users pre register via email.
     * <p>
     * This will create a puser on the server and notify the user.
     *
     * @param preRegisterForm email
     * @return email
     */
    @PostMapping("/puser")
    public PreRegisterForm preRegister(@RequestBody PreRegisterForm preRegisterForm) {
        userService.preRegister(preRegisterForm.getEmail());
        return preRegisterForm;
    }

    /**
     * Verify puser and register user.
     *
     * @param registerForm email, password, uuid
     * @return user
     */
    @PostMapping("/user")
    public User register(@RequestBody RegisterForm registerForm) {
        return userService.register(registerForm.getEmail(), registerForm.getPassword(), registerForm.getUuid());
    }
}
