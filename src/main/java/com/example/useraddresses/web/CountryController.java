package com.example.useraddresses.web;

import com.example.useraddresses.dto.CountryDto;
import com.example.useraddresses.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class CountryController {
    private final CountryService service;

    @Autowired
    public CountryController(final CountryService service) {
        this.service = service;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/countries.json", produces = MediaType.APPLICATION_JSON_VALUE)
    Set<CountryDto> getAllCountries() {
        return service.getAllCountries();
    }
}
