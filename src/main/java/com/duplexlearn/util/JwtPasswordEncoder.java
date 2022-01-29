package com.duplexlearn.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 密码加密器
 *
 * 继承自 BCryptPasswordEncoder
 *
 * @author LoveLonelyTime
 */
@Component
public class JwtPasswordEncoder extends BCryptPasswordEncoder {
}
