package com.example.smart_growth_project.Service;

import com.example.smart_growth_project.Repository.EventRepository;
import com.example.smart_growth_project.Repository.SkillsRepository;
import com.example.smart_growth_project.Repository.UserRepository;
import com.example.smart_growth_project.entityModel.Event_Details;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    //User repository
    @Autowired
    UserRepository userrepo;

    //skillrepo
    @Autowired
    SkillsRepository skillrepo;

    //eventrepo
    @Autowired
    EventRepository eventrepo;

    public boolean addEvent(Event_Details event) {

        eventrepo.save(event);
        return true;
    }

    public boolean deleteEvent(Integer eventid) {

         eventrepo.deleteById(eventid);
         return true;
    }

    public boolean updateEvent(Event_Details event) {

        eventrepo.save(event);
        return true;
    }

    public Event_Details getEvent(Integer eventid) {

        return eventrepo.findById(eventid).orElse(null);

    }
}
