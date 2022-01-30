package com.duplexlearn.model.vo;

import com.duplexlearn.model.enums.Gender;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

/**
 * 用户更新资料时所提交的表单
 *
 * @author LoveLonelyTime
 */
@Data
public class UpdateUserVO {
    /**
     * 用户的昵称
     */
    @NotBlank
    @Length(max = 20)
    private String nickname;

    /**
     * 用户所属的组织
     */
    @Length(max = 20)
    private String organization;

    /**
     * 用户的职位
     */
    @Length(max = 20)
    private String position;

    /**
     * 用户的个人主页
     */
    @Length(max = 50)
    private String homeUrl;

    /**
     * 用户的性别
     */
    private Gender gender;

    /**
     * 用户的年龄
     */
    @Max(value = 150)
    private Integer age;
}
