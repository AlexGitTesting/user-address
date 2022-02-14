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
public class UserServiceImpl implements UserService {

    private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final ValidationService validationService;
    private final UserConverter userConverter;
    private final UserRepository userRepository;
    private final CountryService countryService;
    private final AddressConverter addressConverter;
    private final UserSpecification specification;

    @Autowired
    public UserServiceImpl(final ValidationService validationService, final UserConverter userConverter,
                           final UserRepository userRepository, final CountryService countryService,
                           final AddressConverter addressConverter,
                           final UserSpecification specification) {
        this.validationService = validationService;
        this.userConverter = userConverter;
        this.userRepository = userRepository;
        this.countryService = countryService;
        this.addressConverter = addressConverter;
        this.specification = specification;
    }

    @Override
    @Transactional
    public UserDto createUser(UserDto dto) throws IllegalArgumentException, ValidationCustomException {
        if (!(dto.getId() == null || dto.getId() < 1)) throw new IllegalArgumentException("user is not new");
        validationService.validate(dto, "Validation UserDto before creating ");
        final User user = userConverter.convertToDomain(dto);
        userRepository.save(user);
        // TODO: 14.02.2022 add mailsender
        return userConverter.convertToDto(user);
    }


    @Override
    @Transactional(readOnly = true)
    public AddressedUserDto getUser(Long id) {
        if (id == null || id < 1) throw new IllegalArgumentException("user.validation.id.not.correct");
        final User user = userRepository.getUserById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return new AddressedUserDto(userConverter.convertToDto(user), user.getAddresses().stream().map(addressConverter::convertToDto).collect(toSet()));
    }

    @Override
    @Transactional
    public Long deleteUser(Long id) throws IllegalArgumentException { // TODO: 13.02.2022 EmptyResultDataAccessException
        if (id == null || id < 1) throw new IllegalArgumentException("wrong id");
        userRepository.deleteById(id);
        return id;
    }

    @Override
    @Transactional(readOnly = true)
    public UserModelDto getUserForUpdate(Long id) throws IllegalArgumentException, EntityNotFoundException {
        if (id == null || id < 1) throw new IllegalArgumentException("user.validation.id.not.correct");
        final User user = userRepository.getUserById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        List<AddressDto> addresses = user.getAddresses().isEmpty()
                ? emptyList()
                : user.getAddresses().stream().map(addressConverter::convertToDto).toList();
        return new UserModelDto(userConverter.convertToDto(user), addresses, countryService.getAllCountries());
    }

    @Override
    @Transactional
    // TODO: 14.02.2022 getonly ser
    public AddressedUserDto updateUser(UserDto dto) throws ValidationCustomException, EntityNotFoundException {
        if (dto.getId() == null || dto.getId() < 1)
            throw new IllegalArgumentException("user.validation.id.not.correct");
        validationService.validate(dto, "UserDto");
        final User user = userRepository.getUserById(dto.getId()).orElseThrow(() -> new EntityNotFoundException("User not found"));
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


}
