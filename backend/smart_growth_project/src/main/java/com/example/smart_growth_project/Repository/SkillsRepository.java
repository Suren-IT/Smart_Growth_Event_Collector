package com.example.smart_growth_project.Repository;

import com.example.smart_growth_project.entityModel.Skill_Details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillsRepository extends JpaRepository<Skill_Details,Integer> {
    Skill_Details findBySkillName(String skill1);
}
