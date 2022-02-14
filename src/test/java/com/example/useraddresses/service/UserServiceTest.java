package com.example.useraddresses.service;

import com.example.useraddresses.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.bind.ValidationException;
import java.util.Collections;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    void createUser() throws ValidationException {

//        final UserDto userDto = UserDto.builder().firstname("first").lastname("last").patronymic("patronymic").email("mail@").).build();
//        final UserDto user = userService.createUser(userDto);

    }
}