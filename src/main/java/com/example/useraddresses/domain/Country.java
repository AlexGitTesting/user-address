package com.example.useraddresses.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

import static java.util.Objects.hash;

/**
 * Represents Country entity.
 *
 * @author Alexandr Yefremov
 * @see AuditableEntity
 */
@Entity
@Table(name = "country")
public class Country extends AuditableEntity {
    @Column(name = "name")
    private String name;

    public Country() {
        super();
    }

    public Country(Long id) {
        super(id);
    }

    public Country(String name) {
        super();
        this.name = name;
    }

    public Country(Long id, String name) {
        super(id);
        this.name = name;
    }

    public Country(Long id, LocalDateTime createdDate, LocalDateTime modifiedDate, String name) {
        super(id, createdDate, modifiedDate);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Country country)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getName(), country.getName());
    }

    @Override
    public int hashCode() {
        return hash(super.hashCode(), getName());
    }

    @Override
    public String toString() {
        return "Country{" +
                "id" + getId() +
                " name='" + name + '\'' +
                '}';
    }
}
