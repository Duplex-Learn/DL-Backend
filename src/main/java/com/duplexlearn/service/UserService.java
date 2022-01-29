package com.duplexlearn.service;

import com.duplexlearn.model.*;

/**
 * UserService.
 * <p>
 * Provide user logic services.
 *
 * @author LoveLonelyTime
 */
public interface UserService {
    /**
     * Pre registration.
     *
     * @param puser puser
     * @return puser
     */
    PreRegisterDTO preRegister(PreRegisterDTO preRegisterDTO);

    /**
     * Register.
     *
     * @param puser puser
     * @return user
     */
     UserDTO register(PUserDTO pUserDTO);
}
