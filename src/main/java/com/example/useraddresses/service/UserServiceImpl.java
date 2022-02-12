package com.example.useraddresses.service;

import com.example.useraddresses.domain.User;
import com.example.useraddresses.dto.UserBriefDto;
import com.example.useraddresses.dto.UserDto;
import com.example.useraddresses.dto.UserModel;
import com.example.useraddresses.dto.UserQueryFilter;
import com.example.useraddresses.service.converter.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;

@Service
public class UserServiceImpl implements UserService<User, UserDto, UserModel> {

    private final ValidationService validationService;
    private final UserConverter userConverter;

    @Autowired
    public UserServiceImpl(final ValidationService validationService, final UserConverter userConverter) {
        this.validationService = validationService;
        this.userConverter = userConverter;
    }

    @Override
    public UserDto createUser(UserDto dto) throws IllegalArgumentException, ValidationException {
        if (dto.getId() != null && dto.getId() < 1) throw new IllegalArgumentException("user is not new");
        validationService.validate(dto, "UserDto");
        final User user = userConverter.convertToDomain(dto);
        final UserDto userDto = userConverter.convertToDto(user);
        return dto;

    }

    @Override
    public UserDto getUser(Long id) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }

    @Override
    public UserModel getUserForUpdate(Long id) {
        return null;
    }

    @Override
    public UserDto updateUser(UserDto dto) {
        return null;
    }

    @Override
    public Page<UserBriefDto> getFilteredUsers(UserQueryFilter filter) {
        return null;
    }

    @Override
    public UserDto convertToDto(User user) {
        return null;
    }

    @Override
    public User convertToUserEntity(UserDto dto) {
        return null;
    }
}
