package com.example.useraddresses.service;

import com.example.useraddresses.core.ValidationCustomException;
import com.example.useraddresses.dto.AddressedUserDto;
import com.example.useraddresses.dto.UserDto;
import com.example.useraddresses.dto.UserModelDto;
import com.example.useraddresses.dto.UserQueryFilter;
import org.springframework.data.domain.Page;

import javax.persistence.EntityNotFoundException;

/**
 *
 */
// TODO: 12.02.2022 fill the explanation

public interface UserService {
    UserDto createUser(UserDto dto) throws ValidationCustomException;

    AddressedUserDto getUser(Long id) throws EntityNotFoundException;

    Long deleteUser(Long id);

    UserModelDto getUserProfileForUpdate(Long id) throws EntityNotFoundException;

    AddressedUserDto updateUserProfile(UserDto dto) throws ValidationCustomException, EntityNotFoundException;

    Page<UserDto> getFilteredUsers(UserQueryFilter filter) throws ValidationCustomException;
    boolean ifUserExists(final Long userId);



}
