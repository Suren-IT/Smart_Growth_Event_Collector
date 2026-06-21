package com.example.smart_growth_project.Controller;

import com.example.smart_growth_project.Service.AdminService;
import com.example.smart_growth_project.entityModel.Saved_Event_Details;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5501")
public class AdminController {

    //ADMIN ACCESS
    /*
    * Add event
    * delete event
    * update event
    * view event
    * manage platform
    * */

    @Autowired
    AdminService service;

    //add event
    @PostMapping("/addevent")
    public ResponseEntity<?> addEvent(@RequestBody Saved_Event_Details event){

        if(event.getTitle()!=null &&  event.getPlatform()!=null && event.getLink()!=null &&
                event.getUseremail() != null && event.getDeadline()!=null ){
            Saved_Event_Details s1 =  service.addEvent(event);
            if(s1 != null){
                return  ResponseEntity.status(HttpStatus.CREATED)
                        .body(s1);
            }
            else{
                return ResponseEntity.badRequest()
                        .body("Invalid input");
            }
        }
        return ResponseEntity.badRequest().body("Invalid input ");

    }

    //delete event
    @DeleteMapping("/deleteevent/{eventid}")
    public ResponseEntity<?> deleteEvent(@PathVariable Integer eventid ){

        //to delete first chech wether the id present or not
        if(service.checkEvent(eventid)){

            service.deleteEvent(eventid);
            return ResponseEntity.ok("succesfully deleted ");
        }
        else{
            return  new ResponseEntity<>("Event Not Found ",HttpStatus.NOT_FOUND);
        }

    }

    //update
    @PutMapping("/updateevent")
    public ResponseEntity<?> updateEvent(@RequestBody Saved_Event_Details event){

        //I need to check the input was right or wrong
        if(event.getTitle()!=null &&  event.getPlatform()!=null && event.getLink()!=null &&
                event.getUseremail() != null && event.getDeadline()!=null ){
            //check whether present or not
            Integer id = event.getId();
            if(service.checkEvent(id)){
                service.updateEvent(event);
                return ResponseEntity.ok("Event Updated");
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event Not Found ");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event Not Found ");

    }

    //view
    @GetMapping("/eventid/{eventid}")
    public ResponseEntity<?> getEvent(@PathVariable Integer eventid){
        //check
        if(service.checkEvent(eventid)){
            Saved_Event_Details s1 =  service.getEvent(eventid);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(s1);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid input ");
        }
    }
}
