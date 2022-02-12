package com.example.useraddresses.service;

import javax.xml.bind.ValidationException;

/**
 *
 */
// TODO: 12.02.2022 fill
public interface ValidationService {
    void validate(Object target, String objectName) throws ValidationException;
}
