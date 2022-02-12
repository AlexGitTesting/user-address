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

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country(String name) {
        super();
        this.name = name;
    }

    public Country() {
        super();
    }

    public Country(Long id) {
        super(id);
    }

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                '}';
    }


}
