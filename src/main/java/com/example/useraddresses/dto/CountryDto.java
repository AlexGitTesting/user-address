package com.example.useraddresses.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

/**
 * Represents dto for the name entity.
 *
 * @author Alexandr Yefremov
 */
@JsonDeserialize(builder = CountryDto.Builder.class)
public final class CountryDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 42L;
    @NotNull
    @Min(value = 1)// TODO: 14.02.2022 message
    private final Long id;
    private final String name;

    private CountryDto(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
        private Long id;
        private String name;

        public CountryDto.Builder id(final Long id) {
            this.id = id;
            return this;
        }

        public CountryDto.Builder name(final String name) {
            this.name = name;
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

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "CountryDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
