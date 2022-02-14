package com.example.useraddresses.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

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

    public Country(Long id, String name) {
        super(id);
        this.name = name;
    }

    public Country(Long id, LocalDateTime createdDate, LocalDateTime modifiedDate, String name) {
        super(id, createdDate, modifiedDate);
        this.name = name;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id" + getId() +
                " name='" + name + '\'' +
                '}';
    }
}
