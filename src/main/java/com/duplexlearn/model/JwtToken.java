package com.duplexlearn.model;

/**
 * The JwtToken response body.
 *
 * @author LoveLonelyTime
 */
public class JwtToken {
    private String jwt;

    public JwtToken(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
