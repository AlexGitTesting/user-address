package com.example.useraddresses.service;

import com.example.useraddresses.dao.UserRepository;
import com.example.useraddresses.domain.UserProfile;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

/**
 * Contract for main CRUD operations connected with {@link UserProfile}
 * <p>
 * May be used by {@link UserService} directly or by {@link CacheableUserService} as decorator.
 *
 * @author Alexandr Yefremov
 */
public interface UserCrudService {
    /**
     * Retrieves user by id from the cache or repository.
     *
     * @param id user's id
     * @return {@link UserProfile}
     * @throws EntityNotFoundException if user not found
     */
    @Transactional(readOnly = true)
    UserProfile getUserById(Long id) throws EntityNotFoundException;

    /**
     * Checks if user is present into cache or repository.
     * <p>
     * In case when cache is used, returns cacheable user, otherwise call {@link UserRepository#existsById(Object userId)}.
     * If user exists return user's stub with id, otherwise user stub id will be set to null.
     *
     * @param userId user's id
     * @return user stub with or without id
     */
    @Transactional(readOnly = true)
    UserProfile ifUserExists(final Long userId);

    /**
     * Updates existed or save new user and updates cache(if used)
     *
     * @param user for updating
     * @return updated  user
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    UserProfile saveOrUpdateUser(UserProfile user);

    /**
     * Removes user by id
     *
     * @param id user's id
     * @return removed user's id
     */
    @Transactional
    Long deleteUser(Long id);
}
