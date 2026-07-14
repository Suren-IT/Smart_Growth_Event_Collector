package com.example.smart_growth_project.exceptionpkg;

public class DuplicateUserException extends  RuntimeException{
    public  DuplicateUserException(String msg){
        super(msg);
    }
}
