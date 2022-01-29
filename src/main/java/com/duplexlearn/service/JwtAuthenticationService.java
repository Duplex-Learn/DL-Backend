package com.duplexlearn.service;

import com.duplexlearn.model.LoginFormDTO;

public interface JwtAuthenticationService {
    void authenticate(LoginFormDTO loginFormDTO);
}
