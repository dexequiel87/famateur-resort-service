package com.degg.famateur.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookableAssetDto {

    private String id;

    @Length(min = 1, max = 30)
    private String title;

    private String description;

    private String calendarType;

    private String calendarId;
}
