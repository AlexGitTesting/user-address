package com.example.useraddresses.service;

import com.example.useraddresses.dao.UserRepository;
import com.example.useraddresses.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

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
    public User getUserById(Long id) throws EntityNotFoundException {
        log.debug("method- getUserById; cache does not work");
        return userRepository.getUserById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Override
    @Cacheable(key = "#id")
    public boolean ifUserExists(final Long id) {
        log.debug("method- ifUserExists; cache does not work");
        return userRepository.existsById(id);
    }

    @Override
    @CachePut(key = "#user.id")
    public User saveUser(User user) {
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
