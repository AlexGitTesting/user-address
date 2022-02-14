package com.example.useraddresses.service.converter;

import com.example.useraddresses.domain.Country;
import com.example.useraddresses.dto.CountryDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CountryConverterTest {
    @Autowired
    private  CountryConverter converter;
private static Logger log= LoggerFactory.getLogger(CountryConverterTest.class);
    @Test
    void convertToDomain() {
        final CountryDto ukraine = CountryDto.builder().name("Ukraine").id(100L).build();
        log.info(ukraine.toString());
        final Country domain = converter.convertToDomain(ukraine);
        log.info(domain.toString());
        assertEquals(domain.getId(),ukraine.getId());
        assertNotEquals(domain.getName(),ukraine.getName());
        assertNull(domain.getName());
        assertNull(domain.getCreatedDate());
        assertNull(domain.getModifiedDate());
    }

    @Test
    void convertToDto() {
        final Country ukraine = new Country(100L, LocalDateTime.now(),LocalDateTime.now(),"Ukraine");
        final CountryDto dto = converter.convertToDto(ukraine);
        assertEquals(dto.getId(),ukraine.getId());
        assertEquals(dto.getName(),ukraine.getName());

    }
}