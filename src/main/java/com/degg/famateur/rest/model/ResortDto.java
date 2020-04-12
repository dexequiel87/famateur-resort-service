package com.degg.famateur.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResortDto {

    private String id;

    private String title;

    private String description;

    private Boolean enabled;

    private AddressDto address;

    private List<String> images;
}
