package com.example.smart_growth_project.Controller;

import com.example.smart_growth_project.Service.EventService;
import com.example.smart_growth_project.entityModel.Event_Details;
import com.example.smart_growth_project.entityModel.Skill_Details;
import com.example.smart_growth_project.entityModel.User_Details;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class EventController {

    @Autowired
    EventService service;

    /*user flow
    *register/login
    * select skills
    * view events
    * get recommendations
    * track participations
    */

    //register and Login
    @PostMapping("/register")
    public boolean addUser(@RequestBody User_Details user){
       if( service.addUser(user) != null){
           return true;
       }
       else{
           return false;
       }

    }

    //login
    @PostMapping("/login")
    public boolean loginUser(@RequestParam String email,@RequestParam String password){
        return service.userLogin(email,password);
    }

    //how to select skills
    //1.I need to create a list of skills after that save the data into the skill details & user data
    //set that pick three skills  after that store it in the user as wll as the skill
    @PostMapping("pickskill/")
    public void addSkills(@RequestParam Integer userId, @RequestParam String skill1, @RequestParam String skill2, @RequestParam String skill3){
        service.addSkills(userId,skill1,skill2,skill3);
    }


    //View Events  get all events that are matching with skillset
    @GetMapping("/allevents")
    public List<Event_Details> getAllEvents(){
        return service.getallEvents();
    }

    //search by event Name
    @GetMapping("/allevents/{eventname}")
    public Event_Details getAllEvents(@PathVariable String eventname){
        return service.getEventByName(eventname);
    }




}
