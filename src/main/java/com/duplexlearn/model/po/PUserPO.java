package com.duplexlearn.model.po;

import lombok.Data;

/**
 * 预注册用户实体对象
 *
 * 在用户注册时，首先会验证用户的邮箱，此时将在服务器临时生成预注册对象
 *
 * @author LoveLonelyTime
 */
@Data
public class PUserPO {
    /**
     * 用户的邮箱
     */
    private String email;

    /**
     * 服务器自动为用户生成的 UUID
     */
    private String uuid;
}
