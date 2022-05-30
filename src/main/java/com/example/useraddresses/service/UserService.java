package com.example.useraddresses.service;

import com.example.useraddresses.core.ValidationCustomException;
import com.example.useraddresses.domain.User;
import com.example.useraddresses.dto.AddressedUserDto;
import com.example.useraddresses.dto.UserDto;
import com.example.useraddresses.dto.UserModelDto;
import com.example.useraddresses.dto.UserQueryFilter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

/**
 *
 */
// TODO: 12.02.2022 fill the explanation
@Service
public interface UserService {
    UserDto createUser(UserDto dto) throws ValidationCustomException;

    AddressedUserDto getUser(Long id);

    Long deleteUser(Long id);

    UserModelDto getUserProfileForUpdate(Long id) throws EntityNotFoundException;

    AddressedUserDto updateUserProfile(UserDto dto) throws ValidationCustomException, EntityNotFoundException;

    Page<UserDto> getFilteredUsers(UserQueryFilter filter) throws ValidationCustomException;

    User getUserById(Long id) throws EntityNotFoundException;

    boolean ifUserExists(final Long userId);

    User saveUser(User user);


}
