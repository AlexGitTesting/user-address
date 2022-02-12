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

    public Address(User user, String city, String street, String houseNumber, String flatNumber, Country country) {
        this.user = user;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.flatNumber = flatNumber;
        this.country = country;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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

    // TODO: 10.02.2022 equals hash
// TODO: 12.02.2022 description 
    public boolean isMatchAddress(final Address another)  {
        return this.getCity().equalsIgnoreCase(another.getCity())
                && this.getStreet().equalsIgnoreCase(another.getStreet())
                && this.getHouseNumber().equalsIgnoreCase(another.getHouseNumber())
                && this.getFlatNumber().equalsIgnoreCase(another.getFlatNumber())
                && this.getCountry().getId().equals(another.getCountry().getId());
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
