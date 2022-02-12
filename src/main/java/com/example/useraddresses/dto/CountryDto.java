package com.example.useraddresses.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents dto for the country entity.
 *
 * @author Alexandr Yefremov
 */
@JsonDeserialize(builder = CountryDto.Builder.class)
public final class CountryDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 42L;
    private final Long id;
    private final String country;

    private CountryDto(Builder builder) {
        this.id = builder.id;
        this.country = builder.country;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
        private Long id;
        private String country;

        public CountryDto.Builder id(final Long id) {
            this.id = id;
            return this;
        }

        public CountryDto.Builder country(final String country) {
            this.country = country;
            return this;
        }

        public CountryDto build() {
            return new CountryDto(this);
        }

    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }
}
