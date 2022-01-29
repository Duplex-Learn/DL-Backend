package com.duplexlearn.config;

import com.duplexlearn.filter.JwtRequestFilter;
import com.duplexlearn.service.impl.JwtUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 整个项目的安全配置策略
 *
 * @author LoveLonelyTime
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtUserDetailsServiceImpl jwtUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public WebSecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtUserDetailsServiceImpl jwtUserDetailsService, JwtRequestFilter jwtRequestFilter, PasswordEncoder passwordEncoder) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 暴露 AuthenticationManager 接口
     * @return AuthenticationManager 接口
     * @throws Exception 如果发生异常
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 设置用户服务和密码加密器
        auth.userDetailsService(jwtUserDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO 暂时关闭了 CSRF 注意这是不安全的行为，在生产环境请开启
        http = http.csrf().disable();

        // 开启跨域资源共享
        http = http.cors().and();

        // 设置鉴权范围
        http = http.authorizeHttpRequests()
                .antMatchers("/token").permitAll() // 鉴权请求全部允许
                .antMatchers("/puser").permitAll() // 预注册允许
                .antMatchers(HttpMethod.POST, "/user").permitAll() // 注册允许
                .anyRequest().authenticated() // 其他请求一律需要登录
                .and();

        // 设置鉴权失败终点
        http = http.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and();

        // 关闭 Session 鉴权
        http = http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();

        // 加入我们的 JWT 拦截器
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
