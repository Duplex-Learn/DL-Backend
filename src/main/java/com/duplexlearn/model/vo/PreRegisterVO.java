package com.duplexlearn.model.vo;

import lombok.Data;

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
    private String email;
}
