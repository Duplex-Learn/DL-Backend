package com.duplexlearn.model.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 用户注册的视图对象
 *
 * @author LoveLonelyTime
 */
@Data
public class PUserVO {
    /**
     * 用户的邮箱
     */
    @NotBlank
    @Email
    private String email;

    /**
     * 用户的密码
     */
    @NotBlank
    @Length(min = 6)
    private String password;

    /**
     * 要验证用户的 UUID
     */
    @NotBlank
    private String uuid;
}
