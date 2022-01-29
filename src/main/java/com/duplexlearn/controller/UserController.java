package com.duplexlearn.controller;

import com.duplexlearn.model.dto.PUserDTO;
import com.duplexlearn.model.dto.PreRegisterDTO;
import com.duplexlearn.model.dto.UserDTO;
import com.duplexlearn.model.vo.PUserVO;
import com.duplexlearn.model.vo.PreRegisterVO;
import com.duplexlearn.model.vo.UserVO;
import com.duplexlearn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用户控制器
 * <p>
 * Including user pre registration and registration.
 *
 * @author LoveLonelyTime
 */
@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 用户预注册
     *
     * @param preRegisterVO 用户提交的邮箱
     */
    @PostMapping("/puser")
    public ResponseEntity<?> preRegister(@RequestBody @Valid PreRegisterVO preRegisterVO) {
        // 生成 DTO 对象
        PreRegisterDTO preRegisterDTO = new PreRegisterDTO();
        preRegisterDTO.setEmail(preRegisterVO.getEmail());

        userService.preRegister(preRegisterDTO);

        // 返回 OK
        return ResponseEntity.ok().build();
    }

    /**
     * 注册用户
     *
     * @param pUserVO 预注册用户
     * @return 新用户
     */
    @PostMapping("/user")
    public UserVO register(@RequestBody @Valid PUserVO pUserVO) {
        // 设置 DTO 对象
        PUserDTO pUserDTO = new PUserDTO();
        pUserDTO.setEmail(pUserVO.getEmail());
        pUserDTO.setUuid(pUserVO.getUuid());
        pUserDTO.setPassword(pUserVO.getPassword());
        UserDTO userDTO = userService.register(pUserDTO);

        // 设置 VO 对象
        return createUserVOFromUserDTO(userDTO);
    }

    /**
     * 获取当前已登录的用户
     * @return 用户
     */
    @GetMapping("/user")
    public UserVO getCurrentUser()
    {
        UserDTO userDTO = userService.getCurrentUser();

        // 创建 VO 对象
        return  createUserVOFromUserDTO(userDTO);
    }

    /**
     * 从 VO 到 DTO 的映射
     * @param userDTO DTO 对象
     * @return VO 对象
     */
    private UserVO createUserVOFromUserDTO(UserDTO userDTO)
    {
        UserVO userVO = new UserVO();
        userVO.setEmail(userDTO.getEmail());
        userVO.setId(userDTO.getId());
        userVO.setNickname(userDTO.getNickname());
        userVO.setAge(userDTO.getAge());
        userVO.setGender(userDTO.getGender());
        userVO.setHomeUrl(userDTO.getHomeUrl());
        userVO.setPosition(userDTO.getPosition());
        userVO.setOrganization(userDTO.getOrganization());
        return userVO;
    }
}
