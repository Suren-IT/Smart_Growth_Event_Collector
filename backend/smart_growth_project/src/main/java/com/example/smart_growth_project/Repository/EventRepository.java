package com.example.smart_growth_project.Repository;

import com.example.smart_growth_project.entityModel.Saved_Event_Details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Saved_Event_Details,Integer> {
    Saved_Event_Details findByTitle(String eventName);



    List<Saved_Event_Details> findByUseremail(String email);

    List<Saved_Event_Details> findByTitleIgnoreCase(String eventName);
}
