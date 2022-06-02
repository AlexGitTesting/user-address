package com.example.useraddresses.service;

import com.example.useraddresses.domain.UserProfile;

import javax.persistence.EntityNotFoundException;

/**
 * Provide cacheable functionality.
 *
 * @author Alexandr Yefremov
 */
public interface CacheableUserService {
    UserProfile getUserById(Long id) throws EntityNotFoundException;

    boolean ifUserExists(final Long userId);

    UserProfile saveUser(UserProfile user);

    Long deleteUser(Long id);
}
