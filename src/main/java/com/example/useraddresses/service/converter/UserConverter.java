package com.example.useraddresses.service.converter;

import com.example.useraddresses.domain.User;
import com.example.useraddresses.dto.UserDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = AddressConverter.class)
public interface UserConverter {
    @Mapping(target = "createdDate",ignore = true)
    @Mapping(target = "modifiedDate",ignore = true)
    @Mapping(source = "addressDto",target="addresses")
    User convertToDomain(UserDto source);

    @InheritInverseConfiguration(name = "convertToDomain")
    UserDto convertToDto(User source);
}
