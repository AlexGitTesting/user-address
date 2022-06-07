package com.example.useraddresses.dto;

import com.example.useraddresses.core.RequiredFieldsForCreation;
import com.example.useraddresses.core.RequiredFieldsForUpdating;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * Represents dto for user.
 *
 * @author Alexandr Yefremov
 */
@JsonDeserialize(builder = UserDto.Builder.class)
public final class UserDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 42L;
    @Null(groups = RequiredFieldsForCreation.class, message = "user.validation.id.not.null")
    @NotNull(groups = RequiredFieldsForUpdating.class, message = "user.validation.id.null")
    @Min(value = 1, groups = RequiredFieldsForUpdating.class, message = "user.validation.id.null")
    private final Long id;
    @NotBlank(message = "user.validation.empty.field")
    @Size(max = 100, min = 1, message = "user.validation.field.length")
    private final String firstname;
    @NotBlank(message = "user.validation.empty.field")
    @Size(max = 100, min = 1, message = "user.validation.field.length")
    private final String lastname;
    @Size(max = 100, message = "user.validation.field.length")
    private final String patronymic;// TODO: 02.06.2022 optional
    @NotBlank(message = "user.validation.empty.field")
    @Size(max = 255, min = 1, message = "user.validation.field.length")
    @Email
    private final String email;

    private UserDto(Builder builder) {
        this.id = builder.id;
        this.firstname = builder.firstname;
        this.lastname = builder.lastname;
        this.patronymic = builder.patronymic;
        this.email = builder.email;
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
        private Long id;
        private String firstname;
        private String lastname;
        private String patronymic;
        private String email;


        public UserDto build() {
            return new UserDto(this);
        }

        public UserDto.Builder id(final Long id) {
            this.id = id;
            return this;
        }

        public UserDto.Builder firstname(final String firstname) {
            this.firstname = firstname;
            return this;
        }

        public UserDto.Builder lastname(final String lastname) {
            this.lastname = lastname;
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
    }

    public Optional<Long> getId() {
        return ofNullable(id);
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getEmail() {
        return email;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDto userDto)) return false;
        return Objects.equals(getId(), userDto.getId())
                && getFirstname().equals(userDto.getFirstname())
                && getLastname().equals(userDto.getLastname())
                && getPatronymic().equals(userDto.getPatronymic())
                && getEmail().equals(userDto.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstname(), getLastname(), getPatronymic(), getEmail());
    }
}
