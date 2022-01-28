package com.duplexlearn.config;

import com.duplexlearn.service.JwtUserDetailsService;
import com.duplexlearn.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * The authentication filter.
 * <p>
 * Identify the authorization field in the HTTP header.
 * Verify the validity of token.
 *
 * @author LoveLonelyTime
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtRequestFilter.class);

    private JwtUserDetailsService jwtUserDetailsService;
    private JwtUtil jwtUtil;

    @Autowired
    public void setJwtUserDetailsService(JwtUserDetailsService jwtUserDetailsService) {
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @Autowired
    public void setJwtUtil(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestTokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (requestTokenHeader == null
                || SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }


        if (!requestTokenHeader.startsWith("Bearer ")) {
            log.warn("JWT Token does not begin with Bearer String");
            filterChain.doFilter(request, response);
            return;
        }

        String token = requestTokenHeader.substring(7);
        String username = jwtUtil.getUsernameFromToken(token);

        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);

        if (jwtUtil.validateToken(token, userDetails)) {

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            /*
            After setting the Authentication in the context, we specify that the current user is authenticated.
            So it passes the Spring Security Configurations successfully.
             */
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

        filterChain.doFilter(request, response);
    }
}
