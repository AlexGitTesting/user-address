package com.example.useraddresses.dto;

import java.util.List;
import java.util.Set;

public record UserModelDto(UserDto userDto, Set<CountryDto> allExistedCountries) {
}
