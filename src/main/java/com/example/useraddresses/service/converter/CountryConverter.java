package com.example.useraddresses.service.converter;

import com.example.useraddresses.domain.Country;
import com.example.useraddresses.dto.CountryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CountryConverter {
    @Mapping(target = "createdDate",ignore = true)
    @Mapping(target = "modifiedDate",ignore = true)
    @Mapping(target = "name", ignore = true)
    Country convertToDomain(CountryDto source);


    CountryDto convertToDto(Country source);
}
