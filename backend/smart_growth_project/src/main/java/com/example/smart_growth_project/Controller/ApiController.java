package com.example.smart_growth_project.Controller;

import com.example.smart_growth_project.Service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5501")
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
    public ResponseEntity<?> getApiData(){
        String s1 = service.getApiData();
        if(s1!=null){
            return ResponseEntity.ok(s1);
        }
        return ResponseEntity.notFound().build();
    }

    //get the api data useing the WebClient
    @GetMapping("/dataofapi")
    public ResponseEntity<?> getadata(){

        String s1 = service.getdata();
        if(s1!=null){
            return ResponseEntity.ok(s1);
        }
        return ResponseEntity.notFound().build();
    }
    //get data using Scrapping
    @GetMapping("/scrapping")
    public void getScrapping() throws IOException {
        service.getScrapping();
    }

    //form here on I will go one by one platform
    @GetMapping("/hackerearth")
    public ResponseEntity<?> getHackerEarth(){
        String s1 =  service.getAllEvents(73);
        if(s1!=null){
            return ResponseEntity.ok(s1);
        }
        return ResponseEntity.notFound().build();
    }

    //leetcode
    @GetMapping("/leetcode")
    public  ResponseEntity<?> getLeetcode(){

        String s1 =service.getAllEvents(102);
        if(s1!=null){
            return ResponseEntity.ok(s1);
        }
        return ResponseEntity.notFound().build();
    }

    //hackerrank
    @GetMapping("hackerrank")
    public ResponseEntity<?> gethackrank(){

        String s1 = service.getAllEvents(63);
        if(s1!=null){
            return ResponseEntity.ok(s1);
        }
        return ResponseEntity.notFound().build();
    }

    //codeCheff
    @GetMapping("/codecheff")
    public ResponseEntity<?> getCodeCheff(){

        String s1 =  service.getAllEvents(2);
        if(s1!=null){
            return ResponseEntity.ok(s1);
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/codeforces")
    public ResponseEntity<?> getCodeForces(){

        String s1 = service.getAllEvents(1);
        if(s1!=null){
            return ResponseEntity.ok(s1);
        }
        return ResponseEntity.notFound().build();
    }



}
