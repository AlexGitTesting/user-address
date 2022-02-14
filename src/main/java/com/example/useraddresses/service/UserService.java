package com.example.useraddresses.service;

import com.example.useraddresses.core.ValidationCustomException;
import com.example.useraddresses.domain.User;
import com.example.useraddresses.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

/**
 *
 */
// TODO: 12.02.2022 fill the explanation
@Service
public interface UserService<U extends User,D extends UserDto, M extends UserModelDto,V extends AddressedUserDto> {
    D createUser(D dto) throws IllegalArgumentException, ValidationCustomException;
    V getUser(Long id);
    Long deleteUser(Long id);
    M getUserForUpdate(Long id) throws IllegalArgumentException, EntityNotFoundException;
    V updateUser(D dto) throws ValidationCustomException, EntityNotFoundException;
    Page<V>getFilteredUsers(UserQueryFilter filter) throws ValidationCustomException;



}
