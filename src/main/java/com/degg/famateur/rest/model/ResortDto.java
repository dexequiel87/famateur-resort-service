package com.degg.famateur.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResortDto {

    private String id;

    @Length(min = 1, max = 30)
    private String title;

    private String description;

    private Boolean enabled;

    private AddressDto address;

    private List<String> images;

    private List<BookableAssetDto> bookableAssets;
}
