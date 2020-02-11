package com.degg.famateur.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import javax.validation.constraints.Null;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "resorts")
public class Resort {

    @Id
    @Null
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private Boolean enabled;
}
