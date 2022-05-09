package com.example.useraddresses.service;

import com.example.useraddresses.core.ValidationCustomException;
import com.example.useraddresses.dao.UserRepository;
import com.example.useraddresses.dao.UserSpecification;
import com.example.useraddresses.domain.User;
import com.example.useraddresses.dto.*;
import com.example.useraddresses.service.converter.AddressConverter;
import com.example.useraddresses.service.converter.UserConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

import static java.util.Collections.emptyList;
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


    @Autowired
    public UserServiceImpl(final ValidationService validationService, final UserConverter userConverter,
                           final UserRepository userRepository, final CountryService countryService,
                           final AddressConverter addressConverter, final UserSpecification specification,
                           final EmailService emailService) {
        this.validationService = validationService;
        this.userConverter = userConverter;
        this.userRepository = userRepository;
        this.countryService = countryService;
        this.addressConverter = addressConverter;
        this.specification = specification;
        this.emailService = emailService;

    }

    @Override
    @Transactional
    public UserDto createUser(UserDto dto) throws ValidationCustomException {
        validationService.validate(dto, "Validation UserDto before creating ");
        final User saved = saveUser(userConverter.convertToDomain(dto));
        emailService.sendSimpleMessage(saved.getEmail(), "user.message.subject", "user.message.text");
        return userConverter.convertToDto(saved);
    }


    @Override
    @Transactional(readOnly = true)
    public AddressedUserDto getUser(Long id) {
        final User user = getUserById(id);
        return new AddressedUserDto(userConverter.convertToDto(user), user.getAddresses().stream().map(addressConverter::convertToDto).collect(toSet()));
    }

    @Override
    @Transactional
    @CacheEvict(key ="#id" )
    public Long deleteUser(Long id) {
        userRepository.deleteById(id);
        return id;
    }

    @Override
    @Transactional(readOnly = true)
    public UserModelDto getUserForUpdate(Long id) throws EntityNotFoundException {
        final User user = getUserById(id);
        List<AddressDto> addresses = user.getAddresses().isEmpty()
                ? emptyList()
                : user.getAddresses().stream().map(addressConverter::convertToDto).toList();
        return new UserModelDto(userConverter.convertToDto(user), addresses, countryService.getAllCountries());
    }

    @Override
    @Transactional
    // TODO: 14.02.2022 getonly ser
    public AddressedUserDto updateUser(UserDto dto) throws ValidationCustomException, EntityNotFoundException {
        validationService.validate(dto, "UserDto");
        final User user = getUserById(dto.getId());
        userConverter.convertToDomainTarget(dto, user);
        final User saved = userRepository.save(user);
        Set<AddressDto> addresses = saved.getAddresses().isEmpty()
                ? emptySet()
                : saved.getAddresses().stream().map(addressConverter::convertToDto).collect(toUnmodifiableSet());
        return new AddressedUserDto(userConverter.convertToDto(saved), addresses);
    }


    @Override
    @Transactional(readOnly = true)
    public Page<UserDto> getFilteredUsers(UserQueryFilter filter) throws ValidationCustomException {
        validationService.validate(filter, "UserQueryFilter");
        final Page<User> allUsers = userRepository.findAll(specification.getByFilter(filter), PageRequest.of(filter.getPage(), filter.getLimit()));
        return allUsers.map(userConverter::convertToDto);
    }

    @Cacheable(key ="#id")
    public User getUserById(Long id) throws EntityNotFoundException {
        return userRepository.getUserById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Cacheable(key ="#id")
    public boolean ifUserExists(final Long id) {
        return userRepository.existsById(id);
    }

    @CachePut(key = "#user.id")
    public User saveUser(User user) {
        return userRepository.save(user);
    }


}
