package com.degg.famateur.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Null;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "resorts")
public class Resort {

    @Id
    @Null
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private Boolean enabled;


}
