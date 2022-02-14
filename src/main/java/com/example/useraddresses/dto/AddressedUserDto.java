package com.example.useraddresses.dto;

import java.util.Set;

public record AddressedUserDto(UserDto userDto, Set<AddressDto> addresses) {
}
