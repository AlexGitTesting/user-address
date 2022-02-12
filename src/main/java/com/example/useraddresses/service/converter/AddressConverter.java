package com.example.useraddresses.service.converter;

import com.example.useraddresses.domain.Address;
import com.example.useraddresses.dto.AddressDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = CountryConverter.class)
public interface AddressConverter {

    @Mapping(target = "user",ignore = true)
    @Mapping(source = "countryDto",target="country")
    @Mapping(target = "createdDate",ignore = true)
    @Mapping(target = "modifiedDate",ignore = true)
    Address convertToDomain(AddressDto source);

    @InheritInverseConfiguration(name = "convertToDomain")
    AddressDto convertToDto(Address source);


}
