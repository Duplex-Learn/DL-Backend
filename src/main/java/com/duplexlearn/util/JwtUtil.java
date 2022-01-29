package com.duplexlearn.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * JWT 工具类
 *
 * 帮助生成和验证 JWT
 *
 * @author LoveLonelyTime
 */
@Component
public class JwtUtil {

    /**
     * JWT 有效时间，默认授权时间一个月
     */
    private static final long JWT_TOKEN_VALIDITY = 30 * 24 * 60 * 60;

    /**
     * 签名使用的算法，默认使用 HS256 密钥哈希算法
     */
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    /**
     * 签名密钥
     */
    private SecretKey secretKey;

    @Value("${jwt.secret}")
    public void setSecretKey(String secret){
        secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    /**
     * 从 JWT 中获取字段
     * @param token JWT
     * @param claimsResolver 字段名
     * @param <T> 字段类型
     * @return 字段值
     */
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 从 JWT 中获取所有字段
     * @param token JWT
     * @return 所有字段的集合
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(token).getBody();
    }

    /**
     * 获取过期时间
     * @param token JWT
     * @return 过期时间
     */
    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * 生成 JWT
     * @param userDetails 用户描述
     * @return JWT
     */
    public String generateToken(UserDetails userDetails) {
        // 默认不加什么额外的字段
        return doGenerateToken(Collections.emptyMap(), userDetails.getUsername());
    }

    /**
     * 实际生成 JWT
     * @param claims 额外字段
     * @param subject 用户名
     * @return JWT
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(secretKey, SIGNATURE_ALGORITHM)
                .compact();
    }

    /**
     * 验证 JWT 有效性
     * @param token JWT
     * @param userDetails 用户描述
     * @return JWT 是否有效
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        // 用户名匹配、签名正确且未过期
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * JWT 是否过期
     * @param token JWT
     * @return JWT 是否过期
     */
    private boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * 从 JWT 中获取用户名
     * @param token JWT
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
}
