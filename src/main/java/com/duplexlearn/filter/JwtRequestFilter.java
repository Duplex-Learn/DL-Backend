package com.duplexlearn.filter;

import com.duplexlearn.service.impl.JwtUserDetailsServiceImpl;
import com.duplexlearn.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
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
 * JWT 验证拦截器
 *
 * 自动拦截请求，检查请求头中的 AUTHORIZATION ，如果存在 JWT 则检出用户
 *
 * @author LoveLonelyTime
 */
@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUserDetailsServiceImpl jwtUserDetailsService;
    private final JwtUtil jwtUtil;

    @Autowired
    public JwtRequestFilter(JwtUserDetailsServiceImpl jwtUserDetailsService, JwtUtil jwtUtil) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestTokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // 如果没有 AUTHORIZATION 字段或已经完成验证则调用下一个拦截器
        if (requestTokenHeader == null
                || SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 检查 Bearer 属性
        if (!requestTokenHeader.startsWith("Bearer ")) {
            log.warn("JWT Token does not begin with Bearer String");
            filterChain.doFilter(request, response);
            return;
        }

        // 提取 JWT
        String token = requestTokenHeader.substring(7);
        String username = jwtUtil.getUsernameFromToken(token);

        // 检出用户
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);

        // 验证 JWT
        if (jwtUtil.validateToken(token, userDetails)) {

            // 一切合法则设置好验证用户到当前上下文
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

        filterChain.doFilter(request, response);
    }
}
