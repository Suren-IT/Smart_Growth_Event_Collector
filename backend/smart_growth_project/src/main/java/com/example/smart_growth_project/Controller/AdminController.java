package com.example.smart_growth_project.Controller;

import com.example.smart_growth_project.Service.AdminService;
import com.example.smart_growth_project.Service.EventService;
import com.example.smart_growth_project.entityModel.Event_Details;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
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
    public boolean addEvent(@RequestBody Event_Details event){
        return service.addEvent(event);
    }

    //delete event
    @DeleteMapping("/deleteevent/{eventid}")
    public boolean deleteEvent(@PathVariable Integer eventid ){
        return service.deleteEvent(eventid);
    }

    //update
    @PutMapping("/updateevent")
    public boolean updateEvent(@RequestBody Event_Details event){
        return service.updateEvent(event);
    }

    //view
    @GetMapping("/eventid/{eventid}")
    public Event_Details getEvent(@PathVariable Integer eventid){
        return service.getEvent(eventid);
    }
}
