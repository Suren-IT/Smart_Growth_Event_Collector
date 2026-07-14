package com.example.smart_growth_project.Controller;

import com.example.smart_growth_project.Service.EventService;
import com.example.smart_growth_project.entityModel.Application_Table;
import com.example.smart_growth_project.entityModel.Saved_Event_Details;
import com.example.smart_growth_project.entityModel.User_Details;
import com.example.smart_growth_project.exceptionpkg.ExternalApiException;
import com.example.smart_growth_project.exceptionpkg.GlobalExceptionHandler;
import com.example.smart_growth_project.exceptionpkg.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
    @CrossOrigin(origins = "http://127.0.0.1:5500")
public class EventController {

    @Autowired
    EventService service;

    @Autowired
    GlobalExceptionHandler ex;

    private   Exception ex2= new RuntimeException();
    /*user flow
    *register/login
    * select skills
    * view events
    * get recommendations
    * track participations
    */

    //register and Login
    @PostMapping("/register")
    public ResponseEntity<?> addUser(@RequestBody User_Details user){
        Map<String , String > result = new HashMap<>();
       if( service.addUser(user) != null){
           result.put("status","200");
           result.put("message ","success");
           return  new ResponseEntity<>(result, HttpStatus.CREATED);
       }
       else{

            throw   new RuntimeException("Unable to register the Event  ");
       }

    }

    //login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String email,@RequestParam String password){
        Map<String,String> result = new HashMap<>();
        if(service.userLogin(email,password)){

            result.put("status","200");
            result.put("message","User Found ");

            return ResponseEntity.ok(result);
        }
        else{

            throw   new ResourceNotFoundException("Unable to Login");
        }
    }

    //how to select skills
    //1.I need to create a list of skills after that save the data into the skill details & user data
    //set that pick three skills  after that store it in the user as wll as the skill
    @PostMapping("pickskill/")
    public ResponseEntity<?> addSkills(@RequestParam Integer userId, @RequestParam String skill1, @RequestParam String skill2, @RequestParam String skill3){

        if(service.checkUser(userId) != null){
            service.addSkills(userId,skill1,skill2,skill3);
            return  ResponseEntity.ok("Success");
        }
        else{
            throw   new ResourceNotFoundException("Unable to pick skill");
        }

    }


    //View Events  get all events that are matching with skillset
    @GetMapping("/allevents")
    public ResponseEntity<?> getAllEvents(){
        List<Saved_Event_Details> list =  service.getallEvents();
        if(!list.isEmpty()){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(list);
        }
        else{
            throw   new ExternalApiException("External Api is not working ... !");
        }
    }

    //search by event Name
    @GetMapping("/allevents/{eventname}")
    public ResponseEntity<?> getAllEvents(@PathVariable String eventname){
        List<Saved_Event_Details> s1 =  service.getEventByName(eventname);
        if(!s1.isEmpty()){
            return ResponseEntity.ok(s1);
        }
        else{
            throw   new ExternalApiException("External Api is not working / There is No saved event ... !");
        }
    }


    // Fetch events by the email
    @GetMapping("/finduserlist/{email}")
    public ResponseEntity<?> getUserbyEmail(@PathVariable("email") String email){
         List<Saved_Event_Details> s1 =  service.getSavedEventByUserEmail(email);
         if(!s1.isEmpty()){
             return ResponseEntity.ok(s1.size());
         }
         else{
             throw   new ResourceNotFoundException("Can't find your email");
         }

    }

    //applied jobs
    @PostMapping("/applied")
    public ResponseEntity<?> getAppliedJob(@RequestBody  Application_Table data){
        Application_Table a1 = service.getAppliedJobDetails(data);
        Map<String , String > result = new HashMap<>();
        if( a1 !=null ){
            result.put("status","200");
            result.put("message","data inserted");
            return  new ResponseEntity<>(result, HttpStatus.CREATED);

        }
        throw   new ExternalApiException("External Api is not working ... !");
    }

    //count of applied events
    @GetMapping("/countofApplied/{email}")
    public ResponseEntity<?> getAppliedEvents(@PathVariable String email){
        Map<String , String > result = new HashMap<>();
        long count = service.getCountofEvents(email);
        return new ResponseEntity<>(count,HttpStatus.ACCEPTED);

    }

    @GetMapping("/savedevents/{email}")
    public ResponseEntity<?> getSavedEvent(@PathVariable String email){
        List<Saved_Event_Details>  list = service.getSavedEventByUserEmail(email);
         if (!list.isEmpty()){

            return ResponseEntity.ok(list);
        }else {
             throw   new ResourceNotFoundException("There is no saved event on this  Email ... !");
         }
    }




}
