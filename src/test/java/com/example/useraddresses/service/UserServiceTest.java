package com.example.useraddresses.service;

import com.example.useraddresses.core.ValidationCustomException;
import com.example.useraddresses.dao.UserRepository;
import com.example.useraddresses.domain.User;
import com.example.useraddresses.dto.*;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.test.context.jdbc.Sql;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Sql({"classpath:statements/insert_user.sql",
        "classpath:statements/insert_address.sql"
})
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {
        "classpath:statements/truncate_user.sql",
        "classpath:statements/truncate_address.sql"})
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;
    @SpyBean
    private ValidationService validationService;
    @SpyBean
    private UserRepository userRepository;

    @Test
    void createUser()  {
        final String firstNAme = "firstNAme";
        final String lastName = "lastName";
        final String email = "email@nn.com";
        final UserDto dto = UserDto.builder()
                .firstname(firstNAme)
                .lastname(lastName)
                .email(email)
                .build();
        final UserDto user = userService.createUser(dto);
        assertEquals(dto.getFirstname(), user.getFirstname());
        assertEquals(dto.getLastname(), user.getLastname());
        assertEquals(dto.getEmail(), user.getEmail());
        assertNotNull(user.getId());
        verify(validationService, only()).validate(eq(dto), anyString(), any());
        ArgumentMatcher<User> matcher = userI ->
                userI.getFirstname().equals(firstNAme) &&
                        userI.getLastname().equals(lastName) &&
                        userI.getEmail().equals(email);
        verify(userRepository, only()).save(argThat(matcher));

    }
    @Test
    void createUserValidationFail(){
        final String firstNAme = "firstNAme";
        final String lastName = "lastName";
        final String email = "email@nn.com";
        final UserDto dto = UserDto.builder()
                .firstname(firstNAme)
                .lastname(lastName)
                .email(email)
                .build();
        doThrow(new ValidationCustomException(Collections.singletonMap("key","value"))).when(validationService).validate(any(),anyString(),any());
        assertThrows(ValidationCustomException.class,()->userService.createUser(dto));
        verify(userRepository,never()).save(any());
    }

    @Test
    void getUser() {
        final AddressedUserDto model = userService.getUser(100L);
        final UserDto user = model.userDto();
        assertEquals(user.getId().orElseThrow(), 100L);
        assertEquals(user.getFirstname(), "First name");
        assertEquals(user.getPatronymic(), "Patronymic");
        final Set<AddressDto> addresses = model.addresses();
        assertTrue(addresses.stream().map(AddressDto::getId).collect(toSet()).containsAll(Set.of(100L, 101L)));
        assertFalse(addresses.stream().map(AddressDto::getId).collect(toSet()).contains(102L));
        assertTrue(addresses.stream().map(a -> a.getCountryDto().getId()).collect(toSet()).containsAll(Set.of(26L, 35L)));
        assertFalse(addresses.stream().map(a -> a.getCountryDto().getId()).collect(toSet()).contains(45L));

    }
    @Test
    void getUserCacheable(){
        assertDoesNotThrow(()->userService.getUser(100L));
        userService.getUser(100L);
        verify(userRepository,atLeastOnce()).getUserById(eq(100L));
    }

    @Test
    void deleteUser() {
        assertDoesNotThrow(() -> userService.deleteUser(100L));
    }

    @Test
    void getUserForUpdate() {
        final UserModelDto userForUpdate = userService.getUserProfileForUpdate(100L);
        assertEquals(100L, userForUpdate.userDto().getId().orElseThrow());
        assertFalse(userForUpdate.allExistedCountries().isEmpty());
    }

    @Test
    void updateUser() {
        final UserDto newUser = UserDto.builder().id(100L).firstname("firstname").email("newEmail@jkhjh.com").lastname("new last name").build();
        final AddressedUserDto addressedUserDto = userService.updateUserProfile(newUser);
        final UserDto userDto = addressedUserDto.userDto();
        assertEquals(userDto.getId(), newUser.getId());
        assertEquals(userDto.getEmail(), newUser.getEmail());
        assertEquals(userDto.getLastname(), userDto.getLastname());
    }

    @Test
    void getFilteredUsers() {
        final String firstname = "first101";
        final UserQueryFilter filter = UserQueryFilter.builder().firstname(firstname).build();
        final Page<UserDto> page = userService.getFilteredUsers(filter);
        final List<UserDto> content = page.getContent();
        assertFalse(content.isEmpty());
        assertTrue(content.stream().allMatch(dto -> dto.getFirstname().equalsIgnoreCase(firstname)));
    }

    @Test
    void getFilteredUsers2() {
        final String firstname = "First name";
        final UserQueryFilter filter = UserQueryFilter.builder().firstname(firstname).limit(5).build();
        final Page<UserDto> page = userService.getFilteredUsers(filter);
        final List<UserDto> content = page.getContent();
        assertFalse(content.isEmpty());
        assertTrue(content.stream().allMatch(dto -> dto.getFirstname().equalsIgnoreCase(firstname)));
        assertTrue(content.size() >= 2);
        assertTrue(content.stream().filter(userDto -> userDto.getId().isPresent()).map(userDto -> userDto.getId().get()).collect(toSet()).containsAll(Set.of(100L, 102L)));

    }
}