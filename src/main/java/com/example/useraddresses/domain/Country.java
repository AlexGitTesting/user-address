package com.example.useraddresses.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Represents Country entity.
 *
 * @author Alexandr Yefremov
 * @see AuditableEntity
 */
@Entity
@Table(name = "country")
public class Country extends AuditableEntity{

    private String countryName;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Country(String countryName) {
        this.countryName = countryName;
    }

    public Country() {
    }
    // TODO: 10.02.2022 equals and hash
}
