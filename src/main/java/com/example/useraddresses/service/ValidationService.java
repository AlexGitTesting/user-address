package com.example.useraddresses.service;

import com.example.useraddresses.core.ValidationCustomException;

/**
 * Validates objects using bean validation.
 *
 * @author Alexandr Yefremov
 */
public interface ValidationService {
    void validate(Object target, String objectName,Object... validationHints) throws ValidationCustomException;
}
