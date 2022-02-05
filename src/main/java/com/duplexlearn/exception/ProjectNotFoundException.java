package com.duplexlearn.exception;

public class ProjectNotFoundException extends RuntimeException{
    public ProjectNotFoundException(String slug)
    {
        super("Project Not Found " + slug);
    }
}
