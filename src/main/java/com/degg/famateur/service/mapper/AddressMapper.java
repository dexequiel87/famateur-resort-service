package com.degg.famateur.service.mapper;

import com.degg.famateur.domain.Address;
import com.degg.famateur.rest.model.AddressDto;
import org.mapstruct.Mapper;

@Mapper
public interface AddressMapper {


    /**
     * Map a {@link Address} object to a {@link AddressDto object}
     * @param address the {@link Address} object
     * @return a AddressDto object containing the same attributes as the Address object
     */
    AddressDto toAddressDto(Address address);


    /**
     * Map a {@link AddressDto} object to a {@link Address object}
     * @param addressDto the {@link AddressDto} object
     * @return a {@link Address} object containing the same attributes as the {@link AddressDto} object
     */
    Address toAddress(AddressDto addressDto);
}
