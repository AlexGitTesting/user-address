package com.example.useraddresses.service.converter;

import com.example.useraddresses.domain.Address;
import com.example.useraddresses.dto.AddressDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring",uses = CountryConverter.class)
public interface AddressConverter {

    @Mapping(target = "user",ignore = true)
    @Mapping(source = "countryDto",target="country")
    @Mapping(target = "createdDate",ignore = true)
    @Mapping(target = "modifiedDate",ignore = true)
        // TODO: 14.02.2022 id propustit
    Address convertToDomain(AddressDto source);

//    @InheritInverseConfiguration(name = "convertToDomain")
    @Mapping(target = "countryDto",source = "country")
    AddressDto convertToDto(Address source);

    // TODO: 14.02.2022 use inheritance
    @Mapping(target = "createdDate",ignore = true)
    @Mapping(target = "modifiedDate",ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(source = "countryDto",target="country")
    // TODO: 14.02.2022 id propustit
    void convertToDomainTarget(AddressDto source, @MappingTarget Address target);


}
