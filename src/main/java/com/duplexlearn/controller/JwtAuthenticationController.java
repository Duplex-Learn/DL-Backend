package com.duplexlearn.controller;

import com.duplexlearn.model.LoginFormDTO;
import com.duplexlearn.model.LoginFormVO;
import com.duplexlearn.model.JwtTokenVO;
import com.duplexlearn.service.JwtAuthenticationService;
import com.duplexlearn.service.JwtUserDetailsService;
import com.duplexlearn.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    private JwtUserDetailsService jwtUserDetailsService;
    private JwtAuthenticationService jwtAuthenticationService;
    private JwtUtil jwtUtil;

    @Autowired
    public void setJwtUserDetailsService(JwtUserDetailsService jwtUserDetailsService) {
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @Autowired
    public void setJwtUtil(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Autowired
    public void setJwtAuthenticationService(JwtAuthenticationService jwtAuthenticationService) {
        this.jwtAuthenticationService = jwtAuthenticationService;
    }

    /**
     * Process user login.
     *
     * @param loginForm Email and password
     * @return jwt
     */
    @PostMapping("/token")
    public JwtTokenVO createAuthenticationToken(@RequestBody LoginFormVO loginFormVO) {
        LoginFormDTO loginFormDTO = new LoginFormDTO();
        loginFormDTO.setPassword(loginFormVO.getPassword());
        loginFormDTO.setEmail(loginFormVO.getEmail());

        jwtAuthenticationService.authenticate(loginFormDTO);
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(loginFormVO.getEmail());
        String token = jwtUtil.generateToken(userDetails);
        return new JwtTokenVO(token);
    }
}

