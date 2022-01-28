package com.duplexlearn.exception;

import static java.lang.String.format;

/**
 * IllegalPUserException.
 *
 * @author LoveLonelyTime
 */
public class IllegalPUserException extends RuntimeException {
    public IllegalPUserException(String email) {
        super(format("PUser %s is illegal", email));
    }
}
