package com.example.useraddresses.service;

import com.example.useraddresses.dao.CountryRepository;
import com.example.useraddresses.dto.CountryDto;
import com.example.useraddresses.service.converter.CountryConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static java.util.stream.Collectors.toUnmodifiableSet;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final CountryConverter countryConverter;

    @Autowired
    public CountryServiceImpl(final CountryRepository countryRepository, final CountryConverter countryConverter) {
        this.countryRepository = countryRepository;
        this.countryConverter = countryConverter;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<CountryDto> getAllCountries() {
     return  countryRepository.findAll().stream()
             .map(countryConverter::convertToDto)
             .collect(toUnmodifiableSet());
    }
}
