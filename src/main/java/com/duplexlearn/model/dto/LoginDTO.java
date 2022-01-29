package com.duplexlearn.model.dto;

import lombok.Data;

/**
 * 用户登录表单的数据传输对象
 *
 * 用户登录服务需要的只有用户的邮箱和密码即可验证
 *
 * @author LoveLonelyTime
 */
@Data
public class LoginDTO {
    /**
     * 用户的邮箱
     */
    private String email;

    /**
     * 用户的密码
     */
    private String password;
}
