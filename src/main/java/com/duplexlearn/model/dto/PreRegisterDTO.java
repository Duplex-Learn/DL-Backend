package com.duplexlearn.model.dto;

import lombok.Data;

/**
 * 用户预注册数据传输对象
 *
 * 用户服务在收到预注册服务的时候只需要用户的邮箱
 *
 * @author LoveLonelyTime
 */
@Data
public class PreRegisterDTO {
    /**
     * 用户的邮箱
     */
    private String email;
}
