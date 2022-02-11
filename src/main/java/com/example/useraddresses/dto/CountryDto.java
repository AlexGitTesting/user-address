package com.example.useraddresses.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents dto for the country entity.
 *
 * @author Alexandr Yefremov
 * @see BaseDto
 */
public class CountryDto extends BaseDto {
    @JsonProperty(value = "country")
    private String countryName;
    // TODO: 10.02.2022 complete


    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
