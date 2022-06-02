package com.example.useraddresses.service.converter;

import com.example.useraddresses.domain.UserProfile;
import com.example.useraddresses.dto.UserDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = AddressConverter.class)
public interface UserConverter {
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "modifiedDate", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "id", expression = "java(source.getId().orElse(null))")
    UserProfile convertToDomain(UserDto source);

    @InheritInverseConfiguration(name = "convertToDomain")
    UserDto convertToDto(UserProfile source);

    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "id", expression = "java(source.getId().orElse(null))")
    void convertToDomainTarget(UserDto source, @MappingTarget UserProfile target);
}
