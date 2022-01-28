package com.duplexlearn.exception;

import static java.lang.String.format;


/**
 * UserAlreadyExistsException.
 *
 * @author LoveLonelyTime
 */
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String email) {
        super(format("User %s already exists", email));
    }
}
