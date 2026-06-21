package com.example.smart_growth_project.Controller;

import com.example.smart_growth_project.Service.EventService;
import com.example.smart_growth_project.entityModel.Saved_Event_Details;
import com.example.smart_growth_project.entityModel.User_Details;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5501")
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
    public ResponseEntity<?> addUser(@RequestBody User_Details user){
       if( service.addUser(user) != null){
           return  new ResponseEntity<>("user registration done ", HttpStatus.CREATED);
       }
       else{
           return new ResponseEntity<>("Invalid Input ",HttpStatus.BAD_REQUEST);
       }

    }

    //login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String email,@RequestParam String password){
        if(service.userLogin(email,password)){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("User Found ");
        }
        else{
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found ");
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
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
            return new ResponseEntity<>("Something went wrong ",HttpStatus.BAD_REQUEST);
        }
    }

    //search by event Name
    @GetMapping("/allevents/{eventname}")
    public ResponseEntity<?> getAllEvents(@PathVariable String eventname){
        List<Saved_Event_Details> s1 =  service.getEventByName(eventname);
        if(s1 != null){
            return ResponseEntity.ok(s1);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event Not found ");
        }
    }


    // Fetch events by the email
    @GetMapping("/finduser/{email}")
    public ResponseEntity<?> getUserbyEmail(@PathVariable("email") String email){
         Saved_Event_Details s1 =  service.getSavedEventByUserEmail(email);
         if(s1!=null){
             return ResponseEntity.ok(s1);
         }
         else{
             return ResponseEntity.notFound().build();
         }

    }





}
