package com.duplexlearn.service.impl;

import com.duplexlearn.model.dto.LoginDTO;
import com.duplexlearn.service.JwtAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;


/**
 * JWT 验证服务的默认实现
 *
 * 使用 Spring security 验证用户
 *
 * @author LoveLonelyTime
 */
@Service
public class JwtAuthenticationServiceImpl implements JwtAuthenticationService {
    private final AuthenticationManager authenticationManager;

    @Autowired
    public JwtAuthenticationServiceImpl(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void authenticate(LoginDTO loginDTO) {
        // 验证用户
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
    }
}
