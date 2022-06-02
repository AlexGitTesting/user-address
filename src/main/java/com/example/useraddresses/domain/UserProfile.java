package com.example.useraddresses.domain;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.hash;

/**
 * Represents UserProfile entity.
 *
 * @author Alexandr Yefremov
 * @see AuditableEntity
 */
@Entity
@Table(name = "user_profile")
public class UserProfile extends AuditableEntity {
    @Column(name = "first_name", nullable = false)
    private String firstname;
    @Column(name = "last_name", nullable = false)
    private String lastname;
    @Column(name = "patronymic")
    private String patronymic; // TODO: 02.06.2022 optional
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    // TODO: 10.02.2022 batchsize
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    private List<Address> addresses;

    public UserProfile() {
    }

    public UserProfile(String firstname, String lastname, String patronymic, String email, List<Address> addresses) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.patronymic = patronymic;
        this.email = email;
        this.addresses = addresses;
    }

    public UserProfile(Long id, LocalDateTime createdDate, LocalDateTime modifiedDate, String firstname, String lastname, String patronymic, String email) {
        super(id, createdDate, modifiedDate);
        this.firstname = firstname;
        this.lastname = lastname;
        this.patronymic = patronymic;
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(final String firstName) {
        this.firstname = firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(final String patronymic) {
        this.patronymic = patronymic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public List<Address> getAddresses() {
        if (addresses == null) {
            addresses = new ArrayList<>();
        }
        return addresses;
    }

    public void setAddresses(List<Address> address) {
        this.addresses = address;
        address.forEach(address1 -> address1.setUser(this));
    }

    public void addOneAddress(final Address address) throws IllegalArgumentException {
        final boolean isMatch = getAddresses().stream()
                .anyMatch(address1 -> address1.isMatchAddress(address));
        if (isMatch) throw new IllegalArgumentException("Address already exists for current user");
        getAddresses().add(address);
        address.setUser(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserProfile that)) return false;
        if (!super.equals(o)) return false;
        return getFirstname().equals(that.getFirstname())
                && getLastname().equals(that.getLastname())
                && Objects.equals(getPatronymic(), that.getPatronymic())
                && getEmail().equals(that.getEmail())
                && Objects.equals(getAddresses(), that.getAddresses());
    }

    @Override
    public int hashCode() {
        return hash(super.hashCode(), getFirstname(), getLastname(), getPatronymic(), getEmail(), getAddresses());
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", email='" + email + '\'' +
                ", address=" + addresses +
                '}';
    }
}
