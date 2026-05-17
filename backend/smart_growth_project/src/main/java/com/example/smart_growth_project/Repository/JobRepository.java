package com.example.smart_growth_project.Repository;

import com.example.smart_growth_project.entityModel.Event_Details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Event_Details,Integer> {
}
