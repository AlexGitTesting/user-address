package com.example.useraddresses.service;

import com.example.useraddresses.core.ValidationCustomException;
import com.example.useraddresses.dto.AddressedUserDto;
import com.example.useraddresses.dto.UserDto;
import com.example.useraddresses.dto.UserModelDto;
import com.example.useraddresses.dto.UserQueryFilter;
import org.springframework.data.domain.Page;

import javax.persistence.EntityNotFoundException;

/**
 * Defines contract for  {@link com.example.useraddresses.domain.UserProfile} service.
 *
 * @author Alexandr Yefremov
 */
public interface UserService extends UserCrudService {
    /**
     * Creates new user
     *
     * @param dto holds user's data
     * @return created user's data
     * @throws ValidationCustomException if fields of the dto are invalid, or email is not unique
     */
    UserDto createUser(UserDto dto) throws ValidationCustomException;

    /**
     * Gets user by id.
     *
     * @param id user's id
     * @return user with his addresses
     * @throws EntityNotFoundException if user not found by id
     */
    AddressedUserDto getUser(Long id) throws EntityNotFoundException;

    /**
     * Deletes user by id
     *
     * @param id user's id
     * @return deleted user's id
     */
    Long deleteUser(Long id);

    /**
     * Gets model with user and all countries to update user's data
     *
     * @param id user's id
     * @return model
     * @throws EntityNotFoundException if user by id not found
     */
    UserModelDto getUserProfileForUpdate(Long id) throws EntityNotFoundException;

    /**
     * Updates personal user's data and return user with his addresses
     *
     * @param dto data for update user
     * @return user+addresses
     * @throws EntityNotFoundException user with specified id not found
     */
    AddressedUserDto updateUserProfile(UserDto dto) throws EntityNotFoundException;

    /**
     * Looks for users by filter's params
     *
     * @param filter {@link UserQueryFilter}
     * @return users which mach filter's params
     * @throws ValidationCustomException filter params do not match with conditions
     */
    Page<UserDto> getFilteredUsers(UserQueryFilter filter) throws ValidationCustomException;

    /**
     * Validates user profile data.
     *
     * @param dto UserDto
     * @return user+addresses
     * @throws ValidationCustomException if new params is not valid or params do not match the constraints
     */
    AddressedUserDto validateAndUpdateUserProfile(UserDto dto) throws ValidationCustomException;

}
