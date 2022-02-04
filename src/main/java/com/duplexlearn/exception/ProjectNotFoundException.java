package com.duplexlearn.exception;

public class ProjectNotFoundException extends RuntimeException{
    public ProjectNotFoundException(Long id)
    {
        super("Project Not Found " + id);
    }
}
