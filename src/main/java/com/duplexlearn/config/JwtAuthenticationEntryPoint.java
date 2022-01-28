package com.duplexlearn.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The authentication entry point.
 * <p>
 * It will return 401 status.
 *
 * @author LoveLonelyTime
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        // Return HttpServletResponse.SC_UNAUTHORIZED
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
