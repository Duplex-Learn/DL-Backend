package com.duplexlearn.service;

import com.duplexlearn.model.dto.PUserDTO;
import com.duplexlearn.model.dto.PreRegisterDTO;
import com.duplexlearn.model.dto.UserDTO;

/**
 * 用户服务
 *
 * 提供用户注册、预注册、登录等服务
 *
 * @author LoveLonelyTime
 */
public interface UserService {
    /**
     * 用户预注册
     *
     * @param preRegisterDTO 预注册表单数据传输对象
     */
    void preRegister(PreRegisterDTO preRegisterDTO);

    /**
     * 用户注册
     *
     * @param pUserDTO 预注册用户的数据传输对象
     * @return 新用户的数据传输对象
     */
     UserDTO register(PUserDTO pUserDTO);

    /**
     * 获取当前用户
     *
     * @return 当前用户的数据传输对象
     */
     UserDTO getCurrentUser();

    /**
     * 更新用户信息
     * @param userDTO 用户的数据传输对象
     */
     void updateUser(UserDTO userDTO);
}
