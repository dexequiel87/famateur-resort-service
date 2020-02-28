package com.degg.famateur.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "resorts")
public class Resort {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private Boolean enabled;
}
