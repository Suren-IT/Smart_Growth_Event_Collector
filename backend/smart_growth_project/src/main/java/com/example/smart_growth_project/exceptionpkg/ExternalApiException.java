package com.example.smart_growth_project.exceptionpkg;

public class ExternalApiException extends  RuntimeException{
    public ExternalApiException(String msg){
        super(msg);
    }
}
