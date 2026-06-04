package com.example.smart_growth_project.Repository;

import com.example.smart_growth_project.entityModel.Saved_Event_Details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Saved_Event_Details,Integer> {
}
