package com.duplexlearn.service;

import com.duplexlearn.model.dto.LoginDTO;

/**
 * JWT 验证服务
 *
 * 提供用户名密码验证服务
 *
 * @author LoveLonelyTime
 */
public interface JwtAuthenticationService {
    /**
     * 验证用户
     * @param loginDTO 登录表单的数据传输对象
     */
    void authenticate(LoginDTO loginDTO);
}
