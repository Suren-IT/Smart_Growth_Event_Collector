package com.example.smart_growth_project.entityModel;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Skill_Details {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;
    @Column(nullable = false)
    private String skillName;


    public Skill_Details(String skillName){
        this.skillName=skillName;
    }

}
