package com.duplexlearn.controller;

import com.duplexlearn.model.*;
import com.duplexlearn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User center.
 * <p>
 * Including user pre registration and registration.
 *
 * @author LoveLonelyTime
 */
@RestController
@RequestMapping("/usersys")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Users pre register via email.
     * <p>
     * This will create a puser on the server and notify the user.
     *
     * @param pUser puser
     * @return puser
     */
    @PostMapping("/puser")
    public PreRegisterVO preRegister(@RequestBody @Validated PreRegisterVO preRegisterVO) {
        PreRegisterDTO preRegisterDTO = new PreRegisterDTO();
        preRegisterDTO.setEmail(preRegisterVO.getEmail());

        userService.preRegister(preRegisterDTO);
        return preRegisterVO;
    }

    /**
     * Verify puser and register user.
     *
     * @param pUser puser
     * @return user
     */
    @PostMapping("/user")
    public UserVO register(@RequestBody @Validated PUserVO pUserVO) {
        PUserDTO pUserDTO = new PUserDTO();
        pUserDTO.setEmail(pUserVO.getEmail());
        pUserDTO.setUuid(pUserVO.getUuid());
        pUserDTO.setPassword(pUserVO.getPassword());
        UserDTO userDTO = userService.register(pUserDTO);

        UserVO userVO = new UserVO();
        userVO.setId(userDTO.getId());
        userVO.setEmail(userDTO.getEmail());
        return userVO;
    }
}
