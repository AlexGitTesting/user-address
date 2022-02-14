package com.example.useraddresses.service;

import com.example.useraddresses.dto.CountryDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;
import java.util.function.Predicate;

@SpringBootTest
class CountryServiceTest {
    @Autowired
    private CountryService service;

    @Test
    void getAllCountries() {
        final Set<CountryDto> allCountries = service.getAllCountries();
        Assertions.assertFalse(allCountries.isEmpty());
        Predicate<CountryDto> pr = c -> c.getId().equals(1L);
        final Predicate<CountryDto> and = pr.and(c -> c.getName().equalsIgnoreCase("Україна"));
        Assertions.assertTrue(allCountries.stream().anyMatch(and));
    }
}