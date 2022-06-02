package com.example.useraddresses.dto;

import java.util.Set;

/**
 * DTO which holds user with his addresses.
 *
 * @author Alexandr Yefremov
 */
public record AddressedUserDto(UserDto userDto, Set<AddressDto> addresses) {
}
