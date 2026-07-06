package com.example.smart_growth_project.Controller;

import com.example.smart_growth_project.Service.AdminService;
import com.example.smart_growth_project.entityModel.Saved_Event_Details;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
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

        Map<String,String> result =new HashMap<>();
        if(event.getTitle()!=null &&  event.getPlatform()!=null && event.getLink()!=null &&
                event.getUseremail() != null && event.getDeadline()!=null ){
            Saved_Event_Details s1 =  service.addEvent(event);
            if(s1 != null){
                result.put("status","200");
                result.put("message","Data inserted ");
                return  ResponseEntity.status(HttpStatus.CREATED)
                        .body(result);
            }
            else{
                result.put("status","404");
                result.put("message","sorry Invaid input ");
                return ResponseEntity.badRequest()
                        .body(result);
            }
        }
        return ResponseEntity.badRequest().body(result);

    }

    //delete event
    @DeleteMapping("/deleteevent/{eventid}")
    public ResponseEntity<?> deleteEvent(@PathVariable Integer eventid ){
        Map<String,String> result =new HashMap<>();

        //to delete first chech wether the id present or not
        if(service.checkEvent(eventid)){
            result.put("status","200");
            result.put("message","Data Deleted ");
            service.deleteEvent(eventid);
            return ResponseEntity.ok(result);
        }
        else{
            result.put("status","404");
            result.put("message"," data not found  ");
            return  new ResponseEntity<>(result,HttpStatus.NOT_FOUND);
        }

    }

    //update
    @PutMapping("/updateevent")
    public ResponseEntity<?> updateEvent(@RequestBody Saved_Event_Details event){
        Map<String,String> result =new HashMap<>();

        //I need to check the input was right or wrong
        if(event.getTitle()!=null &&  event.getPlatform()!=null && event.getLink()!=null &&
                event.getUseremail() != null && event.getDeadline()!=null ){
            //check whether present or not
            Integer id = event.getId();
            if(service.checkEvent(id)){
                result.put("status","200");
                result.put("message","data  updated  ");
                service.updateEvent(event);
                return ResponseEntity.ok(result);
            }else{
                result.put("status","404");
                result.put("message","Data not found  ");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
            }
        }
        result.put("status","200");
        result.put("message","Data inserted ");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);

    }

    //view
    @GetMapping("/eventid/{eventid}")
    public ResponseEntity<?> getEvent(@PathVariable Integer eventid){
        Map<String,String> result =new HashMap<>();
        //check
        if(service.checkEvent(eventid)){
            result.put("status","200");
            result.put("message","Data select ");
            Saved_Event_Details s1 =  service.getEvent(eventid);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(s1);
        }
        else{
            result.put("status","404");
            result.put("message","Data not found  ");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }


}
