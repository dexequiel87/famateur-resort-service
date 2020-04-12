package com.degg.famateur.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {
    String id;

    String country;

    String state;

    String city;

    String addressLine1;

    String addressLine2;

    String zipCode;

    Double latitude;

    Double longitude;

}
