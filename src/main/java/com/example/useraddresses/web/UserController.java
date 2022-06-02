package com.example.useraddresses.web;

import com.example.useraddresses.dto.AddressedUserDto;
import com.example.useraddresses.dto.UserDto;
import com.example.useraddresses.dto.UserModelDto;
import com.example.useraddresses.dto.UserQueryFilter;
import com.example.useraddresses.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Handles HTTP requests connected with user
 */
@RestController
@RequestMapping(path = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "User controller")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Creates new user")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/create.json", consumes = MediaType.APPLICATION_JSON_VALUE)
    UserDto createUser(@RequestBody UserDto dto) {
        return userService.createUser(dto);
    }

    @Operation(summary = "Gets user by id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/{id}/get-user.json")
    AddressedUserDto getUser(@PathVariable("id") final Long id) {
        validateParam(id);
        return userService.getUser(id);
    }

    @Operation(summary = "Removes user by id")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}/delete.json")
    Long deleteUser(@PathVariable final Long id) {
        validateParam(id);
        return userService.deleteUser(id);
    }

    @Operation(summary = "Gets user for update withe all countries")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}/get-user-model.json")
    UserModelDto getUserForUpdate(@PathVariable final Long id) {
        validateParam(id);
        return userService.getUserProfileForUpdate(id);
    }

    @Operation(summary = "Updates existed user")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/update.json", consumes = MediaType.APPLICATION_JSON_VALUE)
    AddressedUserDto updateUser(@RequestBody UserDto dto) {
        return userService.updateUserProfile(dto);
    }

    @Operation(summary = "Searches users by filter")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = "/filter.json", consumes = APPLICATION_JSON_VALUE)
    Page<UserDto> getFilteredUsers(@RequestBody UserQueryFilter filter) {
        return userService.getFilteredUsers(filter);
    }

    private void validateParam(Long userId) {
        if (userId == null || userId < 1)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id, must be not null and greater then 0");
    }

}
