package com.example.useraddresses.dto;

/**
 * Represents dto for the address.
 *
 * @author Alexandr Yefremov
 * @see BaseDto
 */
public class AddressDto extends BaseDto {
    private String city;
    private CountryDto countryDto;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public CountryDto getCountryDto() {
        return countryDto;
    }

    public void setCountryDto(CountryDto countryDto) {
        this.countryDto = countryDto;
    }
    // TODO: 10.02.2022  complete
}
