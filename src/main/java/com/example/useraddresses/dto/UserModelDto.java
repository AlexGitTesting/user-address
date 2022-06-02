package com.example.useraddresses.dto;

import java.util.Set;

/**
 * DTO which holds user dto with all existed countries.
 *
 * @author Alexandr Yefremov
 */
public record UserModelDto(UserDto userDto, Set<CountryDto> allExistedCountries) {
}
