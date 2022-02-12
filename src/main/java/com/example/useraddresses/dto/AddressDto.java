package com.example.useraddresses.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents dto for the address.
 *
 * @author Alexandr Yefremov
 */
@JsonDeserialize(builder = AddressDto.Builder.class)
public final class AddressDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 42L;

    private final Long id;
    private final String city;
    private final String street;
    private final String houseNumber;
    private final String flatNumber;
    private final CountryDto countryDto;

    private AddressDto(Builder builder) {
        this.id = builder.id;
        this.city = builder.city;
        this.street = builder.street;
        this.houseNumber = builder.houseNumber;
        this.flatNumber = builder.flatNumber;
        this.countryDto = builder.countryDto;
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {

        private Long id;
        private String city;
        private String street;
        private String houseNumber;
        private String flatNumber;
        private CountryDto countryDto;

        public AddressDto build() {
            return new AddressDto(this);
        }

        public AddressDto.Builder id(final Long id) {
            this.id = id;
            return this;
        }

        public AddressDto.Builder city(final String city) {
            this.city = city;
            return this;
        }

        public AddressDto.Builder street(final String street) {
            this.street = street;
            return this;
        }

        public AddressDto.Builder houseNumber(final String houseNumber) {
            this.houseNumber = houseNumber;
            return this;
        }

        public AddressDto.Builder flatNumber(final String flatNumber) {
            this.flatNumber = flatNumber;
            return this;
        }

        public AddressDto.Builder countryDto(final CountryDto countryDto) {
            this.countryDto = countryDto;
            return this;
        }
    }

    public Long getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public CountryDto getCountryDto() {
        return countryDto;
    }
}
