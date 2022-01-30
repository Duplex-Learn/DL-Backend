package com.duplexlearn.model.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 用户预注册的视图对象
 *
 * @author LoveLonelyTime
 */
@Data
public class PreRegisterVO {
    /**
     * 用户的邮箱
     */
    @NotBlank
    @Email
    @Length(max = 50)
    private String email;
}
