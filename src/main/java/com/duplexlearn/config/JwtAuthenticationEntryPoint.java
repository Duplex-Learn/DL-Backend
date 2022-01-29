package com.duplexlearn.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT 验证失败的终点
 *
 * 默认返回 401
 *
 * @author LoveLonelyTime
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        // 返回 401 HttpServletResponse.SC_UNAUTHORIZED
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
