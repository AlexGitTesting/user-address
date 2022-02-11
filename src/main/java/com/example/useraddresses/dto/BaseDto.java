package com.example.useraddresses.dto;

import java.io.Serial;
import java.io.Serializable;

/**
 * Base dto for stored entities.
 *
 * @author Alexandr Yefremov
 */
public abstract class BaseDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 42L;
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BaseDto(Long id) {
        this.id = id;
    }

    public BaseDto() {
    }
}
