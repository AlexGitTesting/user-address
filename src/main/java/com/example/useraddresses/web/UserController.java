package com.example.useraddresses.web;

import com.example.useraddresses.dto.AddressedUserDto;
import com.example.useraddresses.dto.UserDto;
import com.example.useraddresses.dto.UserModelDto;
import com.example.useraddresses.dto.UserQueryFilter;
import com.example.useraddresses.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/create.json", consumes = MediaType.APPLICATION_JSON_VALUE)
    UserDto createUser(@RequestBody UserDto dto) {
        if (!(dto.getId() == null || dto.getId() < 1)) throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"User is not new");
        return userService.createUser(dto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/{id}/get-user.json")
    AddressedUserDto getUser(@PathVariable("id") final Long id) {
        if (id == null || id < 1) throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid id");
        return userService.getUser(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}/delete.json")
    Long deleteUser(@PathVariable final Long id) {
        if (id == null || id < 1) throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid id");
        return userService.deleteUser(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}/get-user-model.json")
    UserModelDto getUserForUpdate(@PathVariable final Long id) {
        if (id == null || id < 1) throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid id");
        return userService.getUserForUpdate(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/update.json", consumes = MediaType.APPLICATION_JSON_VALUE)
    AddressedUserDto updateUser(@RequestBody UserDto dto) {
        if (dto.getId() == null || dto.getId() < 1) throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"User is  new");
        return userService.updateUser(dto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = "/filter.json", consumes = APPLICATION_JSON_VALUE)
    Page<UserDto> getFilteredUsers(@RequestBody UserQueryFilter filter) {
        return userService.getFilteredUsers(filter);
    }

}