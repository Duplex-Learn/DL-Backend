package com.duplexlearn.model.po;

import com.duplexlearn.model.enums.Gender;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 用户可持久化实体对象
 *
 * 代表了一个用户在数据库中的映射
 *
 * @author LoveLonelyTime
 */
@Entity
@Data
public class UserPO {
    /**
     * 主键，也表示用户的 UID
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 用户的邮箱，是用户登录的唯一凭证
     */
    private String email;

    /**
     * 用户的密码
     */
    private String password;

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
