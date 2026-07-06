package com.example.smart_growth_project.entityModel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Application_Table {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer appllicationId;

    @Column(nullable = false)
    private String email;
    private String eventName;
    private String status;
}

