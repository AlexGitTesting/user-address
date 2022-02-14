package com.example.useraddresses.service.converter;

import com.example.useraddresses.domain.Address;
import com.example.useraddresses.domain.Country;
import com.example.useraddresses.domain.User;
import com.example.useraddresses.dto.AddressDto;
import com.example.useraddresses.dto.CountryDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class AddressConverterTest {
    @Autowired
    private AddressConverter converter;
    private static Logger log = LoggerFactory.getLogger(CountryConverterTest.class);

    @Test
    void convertToDomain() {
        final CountryDto ukraine = CountryDto.builder().name("Ukraine").id(100L).build();
        final AddressDto dto = AddressDto.builder().id(100L).city("Kirilovka").street("Kievskaya").houseNumber("21").flatNumber("45").countryDto(ukraine).build();
        final Address domain = converter.convertToDomain(dto);
        assertEquals(dto.getId() ,domain.getId());
        assertEquals(dto.getCity() ,domain.getCity());
        assertEquals(dto.getStreet() ,domain.getStreet());
        assertEquals(dto.getHouseNumber() ,domain.getHouseNumber());
        assertEquals(dto.getFlatNumber() ,domain.getFlatNumber());
        assertEquals(dto.getCountryDto().getId() ,domain.getCountry().getId());
        assertNull(domain.getCreatedDate());
        assertNull(domain.getModifiedDate());
        assertNull(domain.getCountry().getName());
        assertNull(domain.getCountry().getCreatedDate());
        assertNull(domain.getCountry().getModifiedDate());
    }

    @Test
    void convertToDto() {
        final Country ukraine = new Country(100L, "Ukraine");
        final User user = new User(200L, LocalDateTime.now(),LocalDateTime.now(),"first", "last", "patronimic", "hk@gmail.com");
        final Address address = new Address(user, "Kyiv", "street", "45", "65", ukraine);
        user.addOneAddress(address);
        final AddressDto addressDto = converter.convertToDto(address);
        assertEquals(addressDto.getId() , address.getId());
        assertEquals(addressDto.getCity() , address.getCity());
        assertEquals(addressDto.getStreet() , address.getStreet());
        assertEquals(addressDto.getHouseNumber() , address.getHouseNumber());
        assertEquals(addressDto.getFlatNumber() , address.getFlatNumber());
        assertEquals(addressDto.getCountryDto().getId() , address.getCountry().getId());
        assertEquals(addressDto.getCountryDto().getName() , address.getCountry().getName());



    }

    @Test
    void convertToDomainTarget() {
    }
}