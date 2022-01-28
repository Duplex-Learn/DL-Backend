package com.duplexlearn.model;


/**
 * The register request.
 *
 * @author LoveLonelyTime
 */
public class RegisterForm {
    private String email;
    private String password;
    private String uuid;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
