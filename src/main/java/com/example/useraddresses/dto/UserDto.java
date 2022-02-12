package com.example.useraddresses.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import static java.util.Collections.emptyList;

/**
 * Represents dto for user
 *
 * @author Alexandr Yefremov
 * @see BaseDto
 */
@JsonDeserialize(builder = UserDto.Builder.class)
public final class UserDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 42L;

    private final Long id;
    @NotBlank(message = "user.validation.empty.field")
    @Size(max = 50, min = 1, message = "user.validation.field.length")
    private final String firstname;
    @NotBlank(message = "user.validation.empty.field")
    @Size(max = 50, min = 1, message = "user.validation.field.length")
    private final String lastName;
    @NotBlank(message = "user.validation.empty.field")
    @Size(max = 50, min = 1, message = "user.validation.field.length")
    private final String patronymic;
    @NotBlank(message = "user.validation.empty.field")
    @Size(max = 50, min = 1, message = "user.validation.field.length")
    private final String email;
    private final List<AddressDto> addressDto;

    private UserDto(Builder builder) {
        this.id=builder.id;
        this.firstname = builder.firstname;
        this.lastName = builder.lastName;
        this.patronymic = builder.patronymic;
        this.email = builder.email;
        this.addressDto = builder.addressDto == null ? emptyList() : List.copyOf(builder.addressDto);
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
        private Long id;
        private String firstname;
        private String lastName;
        private String patronymic;
        private String email;
        private List<AddressDto> addressDto;

        public UserDto build() {
            return new UserDto(this);
        }

        public UserDto.Builder id(final Long id) {
            this.id = id;
            return this;
        }

        public UserDto.Builder firstName(final String firstname) {
            this.firstname = firstname;
            return this;
        }

        public UserDto.Builder lastName(final String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserDto.Builder patronymic(final String patronymic) {
            this.patronymic = patronymic;
            return this;
        }

        public UserDto.Builder email(final String email) {
            this.email = email;
            return this;
        }
        public UserDto.Builder addressDto(final List<AddressDto> addressDto) {
            this.addressDto = addressDto;
            return this;
        }
    }

    public Long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getEmail() {
        return email;
    }

    public List<AddressDto> getAddressDto() {
        return addressDto;
    }
}
