package com.degg.famateur.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "resorts")
public class Resort {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String title;

    private String description;

    private Boolean enabled;

    private Address address;

    private List<String> images;

    private List<BookableAsset> bookableAssets;
}
