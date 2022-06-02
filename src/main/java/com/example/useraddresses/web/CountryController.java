package com.example.useraddresses.web;

import com.example.useraddresses.dto.CountryDto;
import com.example.useraddresses.service.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * Handles HTTP requests connected with country.
 *
 * @author Alexandr Yefremov
 */
@RestController
@Tag(name = "Country controller")
public class CountryController {
    private final CountryService service;

    @Autowired
    public CountryController(final CountryService service) {
        this.service = service;
    }

    @Operation(
            summary = "Performs retrieving all existed countries",
            description = "Designed to populate addresses' fields"
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/countries.json", produces = MediaType.APPLICATION_JSON_VALUE)
    Set<CountryDto> getAllCountries() {
        return service.getAllCountries();
    }
}
