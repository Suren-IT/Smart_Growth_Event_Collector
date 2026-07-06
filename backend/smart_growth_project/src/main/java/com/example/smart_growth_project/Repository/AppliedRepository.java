package com.example.smart_growth_project.Repository;

import com.example.smart_growth_project.entityModel.Application_Table;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppliedRepository extends JpaRepository<Application_Table,Integer> {

    Long countByEmail(String email);
}
