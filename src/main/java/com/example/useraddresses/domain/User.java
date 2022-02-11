package com.example.useraddresses.domain;


import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

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
    private Set<Address> address;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(final String firstname) {
        this.firstname = firstname;
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

    public Set<Address> getAddress() {
        if (address == null) {
            address = Collections.emptySet();
        }
        return address;
    }

    public String getFullName() {
        final StringBuilder builder = new StringBuilder();
        return builder.append(getFirstname())
                .append(" ")
                .append(requireNonNullElse(getPatronymic(), ""))
                .append(" ")
                .append(getLastname()).toString();
    }

    public void setAddress(Set<Address> address) {
        this.address = address;
        address.forEach(address1 -> address1.setUser(this));
    }

    public boolean addAddresses(Set<Address> address) {
        address.forEach(address1 -> address1.setUser(this));
        return getAddress().addAll(address);
    }

    public void removeAddress(final Address address) {
        if (getAddress().contains(address)) {
            getAddress().remove(address);
            address.setUser(null);
        } else {
            // TODO: 10.02.2022 buider or local message
            throw new IllegalArgumentException("Current Address: id " + address.getId() + " is not contained in current User: id " + getId() + ", name: " + getFullName());
        }
    }

    public User() {
    }

    public User(String firstname, String lastname, String patronymic, String email, Set<Address> address) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.patronymic = patronymic;
        this.email = email;
        this.address = address;
    }

}
