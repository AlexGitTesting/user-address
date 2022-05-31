package com.example.useraddresses.service;

import com.example.useraddresses.domain.User;

import javax.persistence.EntityNotFoundException;

// TODO: 31.05.2022 fill
public interface CacheableUserService {
    User getUserById(Long id) throws EntityNotFoundException;

    boolean ifUserExists(final Long userId);

    User saveUser(User user);

    Long deleteUser(Long id);
}
