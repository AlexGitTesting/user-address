package com.example.useraddresses.domain;

import javax.persistence.*;

/**
 * Represent address entity.
 *
 * @author Alexandr Yefremov
 * @see AuditableEntity
 */
@Entity
@Table(name = "address")
public class Address extends AuditableEntity {
    @ManyToOne(fetch = FetchType.LAZY) // TODO: 10.02.2022  cascade type merge
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "city", nullable = false)
    private String city;
    @ManyToOne(optional = false)
    @JoinColumn(name = "country_id")
    private Country country;


    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(final Country country) {
        this.country = country;
    }

    public Address() {
    }

    public Address(final User user, final String city, final Country country) {
        this.user = user;
        this.city = city;
        this.country = country;
    }
    // TODO: 10.02.2022 equals hash
}
