package com.duplexlearn.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * The default password encoder.
 *
 * @author LoveLonelyTime
 */
@Component
public class JwtPasswordEncoder extends BCryptPasswordEncoder {
}
