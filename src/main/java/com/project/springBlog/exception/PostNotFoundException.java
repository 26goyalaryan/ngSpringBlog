package com.project.springBlog.exception;

public class PostNotFoundException extends Exception{
    public PostNotFoundException(String message){
        super(message);
    }
}
