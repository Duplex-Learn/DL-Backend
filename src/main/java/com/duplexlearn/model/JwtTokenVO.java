package com.duplexlearn.model;

/**
 * The JwtToken response body.
 *
 * @author LoveLonelyTime
 */
public class JwtTokenVO {
    private String jwt;

    public JwtTokenVO(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
