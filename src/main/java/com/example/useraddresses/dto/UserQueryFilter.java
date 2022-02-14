package com.example.useraddresses.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Optional;

import static java.util.Optional.ofNullable;

// TODO: 12.02.2022 fill
@JsonDeserialize(builder = UserQueryFilter.Builder.class)
public class UserQueryFilter implements Serializable {
    private final Integer page;
    private final Integer limit;
    private final boolean sortingAscending;
    @Size(max = 30, message = "user.validation.field.length")
    @Pattern(regexp = "^([\\p{L} \\d-']*)$", message = "user.validation.field.pattern")
    private final String city;
    @Size(max = 100, message = "user.validation.field.length")
    @Pattern(regexp = "^([\\p{L} \\d-']*)$", message = "user.validation.field.pattern")
    @Size(max = 100, message = "user.validation.field.length")
    private final String firstname;
    @Pattern(regexp = "^([\\p{L} \\d-']*)$", message = "user.validation.field.pattern")
    @Size(max = 100, message = "user.validation.field.length")
    private final String lastname;
    @Pattern(regexp = "^([\\p{L} \\d-']*)$", message = "user.validation.field.pattern")
    @Size(max = 100, message = "user.validation.field.length")
    private final String patronymic;
    private final Long countryId;

    private UserQueryFilter(Builder builder) {
        this.page = builder.page == null || builder.page < 0 ? 0 : builder.page;
        this.limit = builder.limit == null || builder.limit < 0 ? 2 : builder.limit;
        this.sortingAscending = builder.sortingAscending;
        this.city = builder.city;
        this.firstname = builder.firstname;
        this.lastname = builder.lastname;
        this.patronymic = builder.patronymic;
        this.countryId = builder.countryId;
    }

    public static UserQueryFilter.Builder builder() {
        return new UserQueryFilter.Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
        private Integer page;
        private Integer limit;
        private boolean sortingAscending;
        private String city;
        private String firstname;
        private String lastname;
        private String patronymic;
        private Long countryId;

        public UserQueryFilter.Builder page(final Integer page) {
            this.page = page;
            return this;
        }

        public UserQueryFilter.Builder limit(final Integer limit) {
            this.limit = limit;
            return this;
        }

        public UserQueryFilter.Builder sortingAscending(final boolean sortingAscending) {
            this.sortingAscending = sortingAscending;
            return this;
        }

        public UserQueryFilter.Builder city(final String city) {
            this.city = city;
            return this;
        }

        public UserQueryFilter.Builder firstname(final String firstname) {
            this.firstname = firstname;
            return this;
        }

        public UserQueryFilter.Builder lastname(final String lastname) {
            this.lastname = lastname;
            return this;
        }

        public UserQueryFilter.Builder patronymic(final String patronymic) {
            this.patronymic = patronymic;
            return this;
        }

        public UserQueryFilter.Builder countryId(final Long countryId) {
            this.countryId = countryId;
            return this;
        }

        public UserQueryFilter build() {
            return new UserQueryFilter(this);
        }

    }

    public Integer getPage() {
        return this.page;
    }


    public Integer getLimit() {
        return this.limit;
    }


    public boolean isSortingAscending() {
        return sortingAscending;
    }


    public Optional<String> getCity() {
        return ofNullable(city);
    }


    public Optional<String> getFirstname() {
        return ofNullable(firstname);
    }


    public Optional<String> getLastname() {
        return ofNullable(lastname);
    }


    public Optional<String> getPatronymic() {
        return ofNullable(patronymic);
    }

    public Optional<Long> getCountryId() {
        return ofNullable(countryId);
    }

}
