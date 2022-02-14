package com.example.useraddresses.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * Abstract class to hold audit data.
 *
 * @author Alexandr Yefremov
 * @see BaseEntity
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity extends BaseEntity {

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "modified_date", nullable = false)
    private LocalDateTime modifiedDate;

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public AuditableEntity(Long id) {
        super(id);
    }

    public AuditableEntity(Long id, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        super(id);
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public AuditableEntity() {
    }

    @Override
    public String toString() {
        return "AuditableEntity{" +
                "createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                '}';
    }
    // TODO: 10.02.2022 equals and hashCode
}
