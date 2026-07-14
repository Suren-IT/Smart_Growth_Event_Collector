package com.example.smart_growth_project.exceptionpkg;

import java.time.LocalDate;

public class StadardException {
    private  Integer status;
    private  String msg;
    private LocalDate date;

    public StadardException(Integer status,String msg){
        this.status = status;
        this.msg = msg;
        this.date = LocalDate.now();
    }

    public Integer getStatus() {
        return status;
    }


    public String getMsg() {
        return msg;
    }


    public LocalDate getDate() {
        return date;
    }

}
