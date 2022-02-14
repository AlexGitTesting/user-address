package com.example.useraddresses.service;

import com.example.useraddresses.core.ValidationCustomException;

/**
 *
 */
// TODO: 12.02.2022 fill
public interface ValidationService {
    void validate(Object target, String objectName,Object... validationHints) throws ValidationCustomException;
}
