package com.example.smart_growth_project.entityModel;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//        ONLY SAVED EVENTS IS STORING THIS DETAILS
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Saved_Event_Details {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String title;
    private String platform;
    private String deadline;
    private String link;

    private String useremail;
}
