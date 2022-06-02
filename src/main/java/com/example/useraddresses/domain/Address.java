package com.example.useraddresses.domain;

import javax.persistence.*;
import java.util.Objects;

/**
 * Represents address entity.
 *
 * @author Alexandr Yefremov
 * @see AuditableEntity
 */
@Entity
@Table(name = "address")
public class Address extends AuditableEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false) // TODO: 10.02.2022  cascade type merge
    @JoinColumn(name = "user_id", nullable = false)
    private UserProfile user;
    @Column(name = "city", nullable = false)
    private String city;
    @Column(name = "street")
    private String street;
    @Column(name = "house")
    private String houseNumber;
    @Column(name = "flat_number")
    private String flatNumber;
    @ManyToOne(optional = false)
    @JoinColumn(name = "country_id")
    private Country country;

    public Address() {
    }

    public Address(UserProfile user, String city, String street, String houseNumber, String flatNumber, Country country) {
        this.user = user;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.flatNumber = flatNumber;
        this.country = country;
    }

    // TODO: 01.06.2022 replace getters with optional return value for flat number
    public UserProfile getUser() {
        return user;
    }

    public void setUser(UserProfile user) {
        this.user = user;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    /**
     * Compare addresses' fields to prevent duplication.
     *
     * @param another address
     * @return if they same or not
     */
    public boolean isMatchAddress(final Address another) {
        return this.getCity().equalsIgnoreCase(another.getCity())
                && this.getStreet().equalsIgnoreCase(another.getStreet())
                && this.getHouseNumber().equalsIgnoreCase(another.getHouseNumber())
                && this.getFlatNumber().equalsIgnoreCase(another.getFlatNumber())
                && this.getCountry().getId().equals(another.getCountry().getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o.getClass() != this.getClass()) return false;
        if (!super.equals(o)) return false;
        Address address = (Address) o;
        return Objects.equals(getUser(), address.getUser())
                && Objects.equals(getCity(), address.getCity())
                && Objects.equals(getStreet(), address.getStreet())
                && Objects.equals(getHouseNumber(), address.getHouseNumber())
                && Objects.equals(getFlatNumber(), address.getFlatNumber())
                && Objects.equals(getCountry(), address.getCountry());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getUser(), getCity(), getStreet(), getHouseNumber(), getFlatNumber(), getCountry());
    }

    @Override
    public String toString() {
        return "Address{" +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", flatNumber='" + flatNumber + '\'' +
                ", country=" + country +
                '}';
    }

}
