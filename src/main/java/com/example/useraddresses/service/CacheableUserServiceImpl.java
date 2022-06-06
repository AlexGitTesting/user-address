package com.example.useraddresses.service;

import com.example.useraddresses.dao.UserRepository;
import com.example.useraddresses.domain.UserProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

/**
 * Implementation of the {@link CacheableUserService}.
 *
 * @author Alexandr Yefremov
 */
@Service
@CacheConfig(cacheNames = "users")
public class CacheableUserServiceImpl implements CacheableUserService {
    private static Logger log = LoggerFactory.getLogger(CacheableUserServiceImpl.class);
    private final UserRepository userRepository;

    public CacheableUserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Cacheable(key = "#id")
    public UserProfile getUserById(Long id) throws EntityNotFoundException {
        log.debug("method- getUserById; cache does not work");
        return userRepository.getUserById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("UserProfile with id - %d not found", id)));
    }

    @Override
    @Cacheable(key = "#id")
    public UserProfile ifUserExists(final Long id) {
        log.debug("method- ifUserExists; cache does not work");
        final UserProfile userProfile = new UserProfile();
        if (userRepository.existsById(id)) {
            userProfile.setId(id);
        }
        return userProfile;
    }

    @Override
    @CachePut(key = "#user.id")
    public UserProfile saveOrUpdateUser(UserProfile user) {
        return userRepository.save(user);
    }

    @Override
    @CacheEvict(key = "#id")
    public Long deleteUser(Long id) {
        log.debug("method-deleteUser; user was deleted");
        userRepository.deleteById(id);
        return id;
    }
}
