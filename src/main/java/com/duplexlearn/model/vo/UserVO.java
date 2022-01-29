package com.duplexlearn.model.vo;

import com.duplexlearn.model.enums.Gender;
import lombok.Data;

/**
 * 用户视图对象
 *
 * @author LoveLonelyTime
 */
@Data
public class UserVO {
    /**
     * 用户的 UID
     */
    private Long id;

    /**
     * 用户的邮箱
     */
    private String email;

    /**
     * 用户的昵称
     */
    private String nickname;

    /**
     * 用户所属的组织
     */
    private String organization;

    /**
     * 用户的职位
     */
    private String position;

    /**
     * 用户的个人主页
     */
    private String homeUrl;

    /**
     * 用户的性别
     */
    private Gender gender;

    /**
     * 用户的年龄
     */
    private Integer age;
}
