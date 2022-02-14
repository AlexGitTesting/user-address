package com.example.useraddresses.service.converter;

import com.example.useraddresses.domain.User;
import com.example.useraddresses.dto.UserDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = AddressConverter.class)
public interface UserConverter {
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "modifiedDate", ignore = true)
    @Mapping( target = "addresses",ignore = true)
    User convertToDomain(UserDto source);

    @InheritInverseConfiguration(name = "convertToDomain")
    UserDto convertToDto(User source);

    @Mapping(target = "addresses", ignore = true)
    void convertToDomainTarget(UserDto source, @MappingTarget User target);
}
