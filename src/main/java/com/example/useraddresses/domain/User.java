package com.example.useraddresses.domain;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNullElse;

/**
 * Represents User entity.
 *
 * @author Alexandr Yefremov
 * @see AuditableEntity
 */
@Entity
@Table(name = "user_profile")
public class User extends AuditableEntity {
    @Column(name = "first_name", nullable = false)
    private String firstname;
    @Column(name = "last_name", nullable = false)
    private String lastname;
    @Column(name = "patronymic")
    private String patronymic;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    // TODO: 10.02.2022 batchsize
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Address> addresses;

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

    public String getFullName() {
        final StringBuilder builder = new StringBuilder();
        return builder.append(getFirstname())
                .append(" ")
                .append(requireNonNullElse(getPatronymic(), ""))
                .append(" ")
                .append(getLastname()).toString();
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

    public boolean addAddresses(List<Address> address) {
        address.forEach(address1 -> address1.setUser(this));
        return getAddresses().addAll(address);
    }

    public void removeAddress(final Address address) {
        if (getAddresses().contains(address)) {
            getAddresses().remove(address);
            address.setUser(null);
        } else {
            // TODO: 10.02.2022 buider or local message
            throw new IllegalArgumentException("Current Address: id " + address.getId() + " is not contained in current User: id " + getId() + ", name: " + getFullName());
        }
    }

    public User() {
    }

    public User(String firstname, String lastname, String patronymic, String email, List<Address> addresses) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.patronymic = patronymic;
        this.email = email;
        this.addresses = addresses;
    }

    public User(Long id, LocalDateTime createdDate, LocalDateTime modifiedDate, String firstname, String lastname, String patronymic, String email) {
        super(id, createdDate, modifiedDate);
        this.firstname = firstname;
        this.lastname = lastname;
        this.patronymic = patronymic;
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", email='" + email + '\'' +
                ", address=" + addresses +
                '}';
    }
}
