package com.duplexlearn.model.vo;

import lombok.Data;

/**
 * JWT 令牌视图对象
 *
 * 用户在登录成功之后，返回一个 JWT 令牌
 *
 * @author LoveLonelyTime
 */
@Data
public class JwtTokenVO {
    /**
     * JWT 令牌
     */
    private String jwt;
}
