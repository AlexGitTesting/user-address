package com.example.useraddresses.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents dto for user
 *
 * @author Alexandr Yefremov
 * @see BaseDto
 */
public final class UserDto extends BaseDto{
    @JsonProperty(value = "full name")
    private final  String fullName;
    private final  String email;
    private final List<AddressDto> addressDto;

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public List<AddressDto> getAddressDto() {
        return addressDto;
    }

    public static  UserDto of(Long id, String fullName, String email, List<AddressDto> addressDto) {
        return  new UserDto(id, fullName,email,addressDto);
    }

    private UserDto(Long id, String fullName, String email, List<AddressDto> addressDto) {
        super(id);
        this.fullName = fullName;
        this.email = email;
        this.addressDto = addressDto;
        // TODO: 10.02.2022 do copy of addresses
    }
}
