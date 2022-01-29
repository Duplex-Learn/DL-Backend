package com.duplexlearn.model.dto;

import lombok.Data;

/**
 * 注册用户的数据传输对象
 *
 * 用户服务需要用户的邮箱和密码作为新注册的用户，而 UUID 则用于验证预注册用户的有效性
 *
 * @author LoveLonelyTime
 */
@Data
public class PUserDTO {
    /**
     * 用户的邮箱
     */
    private String email;

    /**
     * 用户的密码
     */
    private String password;

    /**
     * 用户的 UUID
     */
    private String uuid;
}
