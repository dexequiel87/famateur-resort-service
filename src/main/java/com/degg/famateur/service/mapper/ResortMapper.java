package com.degg.famateur.service.mapper;

import com.degg.famateur.domain.Resort;
import com.degg.famateur.rest.model.ResortDto;
import org.mapstruct.Mapper;

@Mapper(uses = {AddressMapper.class, BookableAssetMapper.class})
public interface ResortMapper {

    ResortDto toResortDto(Resort resort);

    Resort toResort(ResortDto resort);

}
