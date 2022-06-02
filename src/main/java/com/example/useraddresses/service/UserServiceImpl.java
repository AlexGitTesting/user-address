package com.example.useraddresses.service;

import com.example.useraddresses.core.RequiredFieldsForCreation;
import com.example.useraddresses.core.RequiredFieldsForUpdating;
import com.example.useraddresses.core.ValidationCustomException;
import com.example.useraddresses.dao.UserRepository;
import com.example.useraddresses.dao.UserSpecification;
import com.example.useraddresses.domain.UserProfile;
import com.example.useraddresses.dto.*;
import com.example.useraddresses.service.converter.AddressConverter;
import com.example.useraddresses.service.converter.UserConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Set;

import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.Collectors.toUnmodifiableSet;

@Service
@CacheConfig(cacheNames = "users")
public class UserServiceImpl implements UserService {

    private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final ValidationService validationService;
    private final UserConverter userConverter;
    private final UserRepository userRepository;
    private final CountryService countryService;
    private final AddressConverter addressConverter;
    private final UserSpecification specification;
    private final EmailService emailService;
    private final CacheableUserService cacheableUserService;


    @Autowired
    public UserServiceImpl(final ValidationService validationService, final UserConverter userConverter,
                           final UserRepository userRepository, final CountryService countryService,
                           final AddressConverter addressConverter, final UserSpecification specification,
                           final EmailService emailService, final CacheableUserService cacheableUserService) {
        this.validationService = validationService;
        this.userConverter = userConverter;
        this.userRepository = userRepository;
        this.countryService = countryService;
        this.addressConverter = addressConverter;
        this.specification = specification;
        this.emailService = emailService;
        this.cacheableUserService = cacheableUserService;
    }

    @Override
    @Transactional
    public UserDto createUser(UserDto dto) throws ValidationCustomException {
        validationService.validate(dto, "Validation UserDto before creating ", RequiredFieldsForCreation.class);
        final UserProfile saved = cacheableUserService.saveUser(userConverter.convertToDomain(dto));
        emailService.sendSimpleMessage(saved.getEmail(), "user.message.subject", "user.message.text");
        return userConverter.convertToDto(saved);
    }


    @Override
    @Transactional(readOnly = true)
    public AddressedUserDto getUser(Long id) {
        final UserProfile user = cacheableUserService.getUserById(id);
        return new AddressedUserDto(userConverter.convertToDto(user), user.getAddresses().stream().map(addressConverter::convertToDto).collect(toSet()));
    }

    @Override
    @Transactional
    public Long deleteUser(Long id) {
        cacheableUserService.deleteUser(id);
        return id;
    }

    @Override
    @Transactional(readOnly = true)
    public UserModelDto getUserProfileForUpdate(Long id) throws EntityNotFoundException {
        final UserProfile user = cacheableUserService.getUserById(id);
        return new UserModelDto(userConverter.convertToDto(user), countryService.getAllCountries());
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public AddressedUserDto updateUserProfile(UserDto dto) throws ValidationCustomException, EntityNotFoundException {
        validationService.validate(dto, "UserDto", RequiredFieldsForUpdating.class);
        final UserProfile user = cacheableUserService.getUserById(dto.getId().orElseThrow());
        userConverter.convertToDomainTarget(dto, user);
        final UserProfile saved = cacheableUserService.saveUser(user);
        Set<AddressDto> addresses = saved.getAddresses().isEmpty()
                ? emptySet()
                : saved.getAddresses().stream().map(addressConverter::convertToDto).collect(toUnmodifiableSet());
        return new AddressedUserDto(userConverter.convertToDto(saved), addresses);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDto> getFilteredUsers(UserQueryFilter filter) throws ValidationCustomException {
        validationService.validate(filter, "UserQueryFilter");
        final Page<UserProfile> allUsers = userRepository.findAll(specification.getByFilter(filter), PageRequest.of(filter.getPage(), filter.getLimit()));
        return allUsers.map(userConverter::convertToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean ifUserExists(Long userId) {
        return cacheableUserService.ifUserExists(userId);
    }


}
