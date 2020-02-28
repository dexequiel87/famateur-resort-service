package com.degg.famateur.service.mapper;

import com.degg.famateur.domain.Resort;
import com.degg.famateur.rest.model.ResortDto;
import org.mapstruct.Mapper;

@Mapper
public interface ResortMapper {

    /**
     * Map a {@link Resort} object to a {@link ResortDto object}
     * @param resort the Resort object
     * @return a ResortDto object containing the same attributes as the Resort object
     */
    ResortDto toResortDto(Resort resort);



    /**
     * Map a {@link ResortDto} object to a {@link Resort object}
     * @param resort the Resort object
     * @return a ResortDto object containing the same attributes as the Resort object
     */
    Resort toResort(ResortDto resort);

}
