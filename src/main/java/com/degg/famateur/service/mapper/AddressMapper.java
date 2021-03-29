package com.degg.famateur.service.mapper;

import com.degg.famateur.domain.Address;
import com.degg.famateur.rest.model.AddressDto;
import org.mapstruct.Mapper;

@Mapper
public interface AddressMapper {

    AddressDto toAddressDto(Address address);

    Address toAddress(AddressDto addressDto);
}
