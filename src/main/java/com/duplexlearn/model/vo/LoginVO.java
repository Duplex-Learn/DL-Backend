package com.duplexlearn.model.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 登录表单的请求视图对象
 *
 * @author LoveLonelyTime
 */
@Data
public class LoginVO {
    /**
     * 用户的邮箱
     */
    @Email
    @NotBlank
    @Length(max = 50)
    private String email;

    /**
     * 用户的密码，至少 6 位，最大 18 位
     */
    @Length(min = 6,max = 18)
    private String password;
}
