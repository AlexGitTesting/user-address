package com.example.useraddresses.service;

import com.example.useraddresses.domain.User;
import com.example.useraddresses.dto.UserBriefDto;
import com.example.useraddresses.dto.UserDto;
import com.example.useraddresses.dto.UserModel;
import com.example.useraddresses.dto.UserQueryFilter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;

/**
 *
 */
// TODO: 12.02.2022 fill the explanation
@Service
public interface UserService<U extends User,D extends UserDto, M extends UserModel> {
    D createUser(D dto) throws IllegalArgumentException, ValidationException;
    D getUser(Long id);
    void deleteUser(Long id);
    M getUserForUpdate(Long id);
    D updateUser(D dto);
    Page<UserBriefDto>getFilteredUsers(UserQueryFilter filter);
    D convertToDto(U user);
    U convertToUserEntity(D dto);

}
