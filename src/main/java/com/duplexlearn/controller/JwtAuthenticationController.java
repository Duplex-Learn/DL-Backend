package com.duplexlearn.controller;

import com.duplexlearn.model.dto.LoginDTO;
import com.duplexlearn.model.vo.LoginVO;
import com.duplexlearn.model.vo.JwtTokenVO;
import com.duplexlearn.service.JwtAuthenticationService;
import com.duplexlearn.service.impl.JwtUserDetailsServiceImpl;
import com.duplexlearn.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 鉴权控制器
 *
 * 提供用户登录（鉴权）接口
 *
 * @author LoveLonelyTime
 */
@RestController
public class JwtAuthenticationController {

    private final JwtUserDetailsServiceImpl jwtUserDetailsService;
    private final JwtAuthenticationService jwtAuthenticationService;
    private final JwtUtil jwtUtil;

    @Autowired
    public JwtAuthenticationController(JwtUserDetailsServiceImpl jwtUserDetailsService, JwtAuthenticationService jwtAuthenticationService, JwtUtil jwtUtil) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtAuthenticationService = jwtAuthenticationService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 请求 JWT 令牌资源，处理用户登录
     *
     * @param loginForm 邮箱和密码
     * @return jwt 返回 JWT 令牌
     */
    @PostMapping("/token")
    public JwtTokenVO createAuthenticationToken(@RequestBody @Valid LoginVO loginForm) {
        // 构建 DTO 对象
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setPassword(loginForm.getPassword());
        loginDTO.setEmail(loginForm.getEmail());

        // 验证用户是否合法
        jwtAuthenticationService.authenticate(loginDTO);

        // 合法则检出用户并生成 JWT
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(loginForm.getEmail());
        String token = jwtUtil.generateToken(userDetails);

        // 生成 VO 对象
        JwtTokenVO jwtTokenVO = new JwtTokenVO();
        jwtTokenVO.setJwt(token);
        return jwtTokenVO;
    }
}

