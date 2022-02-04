package com.duplexlearn.exception;

public class IllegalProjectException extends RuntimeException{
    public IllegalProjectException(String url,Throwable t)
    {
        super("Illegal Project " + url,t);
    }
}
