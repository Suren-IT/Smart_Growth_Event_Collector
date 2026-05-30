package com.example.smart_growth_project.Controller;

import com.example.smart_growth_project.Service.ApiService;
import com.example.smart_growth_project.entityModel.Event_Details;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class ApiController {

    /*
    * here I will create the Api backend for each one of the platforms so that can able to send the data to backend data
    * platform: leetcode,hackathon , etc...
    *
    * */

    @Autowired
    private ApiService service;


    //get the api content with the restTempplate
    @GetMapping("/api")
    public String getApiData(){
        return service.getApiData();
    }

    //get the api data useing the WebClient
    @GetMapping("/dataofapi")
    public String getadata(){
        return  service.getdata();
    }
    //get data using Scrapping
    @GetMapping("/scrapping")
    public void getScrapping() throws IOException {
        service.getScrapping();
    }

    //form here on I will go one by one platform
    @GetMapping("/hackerearth")
    public String getHackerEarth(){
        return  service.getAllEvents(73);
    }

    //leetcode
    @GetMapping("/leetcode")
    public  String getLeetcode(){
        return  service.getAllEvents(102);
    }

    //hackerrank
    @GetMapping("hackerrank")
    public String gethackrank(){
        return service.getAllEvents(63);
    }

    //codeCheff
    @GetMapping("/codecheff")
    public String getCodeCheff(){
        return  service.getAllEvents(2);
    }
    @GetMapping("/codeforces")
    public String getCodeForces(){
        return service.getAllEvents(1);
    }



}
