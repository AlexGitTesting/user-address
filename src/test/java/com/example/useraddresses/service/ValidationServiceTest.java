package com.example.useraddresses.service;

import com.example.useraddresses.core.RequiredFieldsForCreation;
import com.example.useraddresses.core.ValidationCustomException;
import com.example.useraddresses.dto.AddressDto;
import com.example.useraddresses.dto.CountryDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@SpringBootTest
class ValidationServiceTest {

    @Autowired
    private ValidationService service;

    @Test
    void validateAddressDtoCreation() {
        final CountryDto countryDto = CountryDto.builder().id(5L).name("updatedNameCountry").build();
        final AddressDto dto = AddressDto.builder()
                .id(null)
                .flatNumber("updated12")
                .houseNumber("updated12")
                .street("updatedNameStreet")
                .city("updatedName City")
                .countryDto(countryDto)
                .build();
        assertDoesNotThrow(()->service.validate(dto,"AddressDto", RequiredFieldsForCreation.class));
    }
    @Test
    void validateAddressIdMustBeNull() {
        final CountryDto countryDto = CountryDto.builder().id(5L).name("updatedNameCountry").build();
        final AddressDto dto = AddressDto.builder()
                .id(12L)
                .flatNumber("updated12")
                .houseNumber("updated12")
                .street("updatedNameStreet")
                .city("updatedName City")
                .countryDto(countryDto)
                .build();
        assertThrowsExactly(ValidationCustomException.class,()->service.validate(dto,"AddressDto", RequiredFieldsForCreation.class));
    }
    @Test
    void validateAddressDtoWrongPattern() {
        final CountryDto countryDto = CountryDto.builder().id(5L).name("updatedNameCountry").build();
        final AddressDto dto = AddressDto.builder()
                .id(null)
                .flatNumber("upda&ted12")
                .houseNumber("updated12")
                .street("updatedNameStreet")
                .city("updatedName City")
                .countryDto(countryDto)
                .build();
        assertThrowsExactly(ValidationCustomException.class,()->service.validate(dto,"AddressDto", RequiredFieldsForCreation.class));
    }
    @Test
    void validateAddressDtoWrongIdParam() {
        final CountryDto countryDto = CountryDto.builder().id(5L).name("updatedNameCountry").build();
        final AddressDto dto = AddressDto.builder()
                .id(null)
                .flatNumber("updated12")
                .houseNumber("updated12")
                .street("updatedNameStreet")
                .city("updatedName City")
                .countryDto(countryDto)
                .build();
        assertThrowsExactly(ValidationCustomException.class,()->service.validate(dto,"AddressDto", RequiredFieldsForCreation.class));
    }
    @Test
    void validateAddressDtoWrongIdParamNegativeValue() {
        final CountryDto countryDto = CountryDto.builder().id(5L).name("updatedNameCountry").build();
        final AddressDto dto = AddressDto.builder()
                .id(-13L)
                .flatNumber("updated12")
                .houseNumber("updated12")
                .street("updatedNameStreet")
                .city("updatedName City")
                .countryDto(countryDto)
                .build();
        assertThrowsExactly(ValidationCustomException.class,()->service.validate(dto,"AddressDto", RequiredFieldsForCreation.class));
    }
}