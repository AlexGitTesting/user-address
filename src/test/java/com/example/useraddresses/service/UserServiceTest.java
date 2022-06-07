package com.example.useraddresses.service;

import com.example.useraddresses.core.ValidationCustomException;
import com.example.useraddresses.dao.UserRepository;
import com.example.useraddresses.domain.UserProfile;
import com.example.useraddresses.dto.*;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockReset;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
@TestPropertySource(properties = "logging.level.org.springframework.cache=trace")
class UserServiceTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;
    @SpyBean(reset = MockReset.BEFORE)
    private ValidationService validationService;
    @SpyBean(reset = MockReset.BEFORE)
    private UserRepository userRepository;

    @Test
    void createUser() {
        clearInvocations(userRepository);
        final String firstNAme = "firstNAme****";
        final String lastName = "lastName***";
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
        ArgumentMatcher<UserProfile> matcher = userI ->
                userI.getFirstname().equals(firstNAme) &&
                        userI.getLastname().equals(lastName) &&
                        userI.getEmail().equals(email);
        verify(userRepository, times(1)).save(argThat(matcher));

    }

    @Test
    void createUserNotUniqueEmail() {
        final String firstNAme = "First name";
        final String lastName = "Last Name";
        final String email = "dsfg@gmail.com";
        final UserDto dto = UserDto.builder()
                .firstname(firstNAme)
                .lastname(lastName)
                .email(email)
                .build();

        assertThrows(ValidationCustomException.class, () -> userService.createUser(dto));

    }

    @Test
    void createUserNotUniqueFullName() {
        final String firstNAme = "First name";
        final String lastName = "Last Name";
        final String email = "dsssfg@gmail.com";
        final UserDto dto = UserDto.builder()
                .firstname(firstNAme)
                .lastname(lastName)
                .email(email)
                .build();

        assertThrows(ValidationCustomException.class, () -> userService.createUser(dto));

    }

    @Test
    void createUserValidationFail() {
        final String firstNAme = "firstNAme1";
        final String lastName = "lastName1";
        final String email = "email@nn.com";
        final UserDto dto = UserDto.builder()
                .firstname(firstNAme)
                .lastname(lastName)
                .email(email)
                .build();
        doThrow(new ValidationCustomException(Collections.singletonMap("key", "value"))).when(validationService).validate(any(), anyString(), any());
        assertThrows(ValidationCustomException.class, () -> userService.createUser(dto));
        ArgumentMatcher<UserProfile> matcher = userI ->
                userI.getFirstname().equals(firstNAme) &&
                        userI.getLastname().equals(lastName) &&
                        userI.getEmail().equals(email);
        verify(userRepository, never()).save(argThat(matcher));
    }

    @Test
    void getUser() {
        final long id = 102L;
        final AddressedUserDto model = userService.getUser(id);
        final UserDto user = model.userDto();
        assertEquals(user.getId().orElseThrow(), id);
        assertEquals(user.getFirstname(), "First name");
        assertEquals(user.getPatronymic(), "patronymic102");
        final Set<AddressDto> addresses = model.addresses();
        final Set<Long> addressIds = addresses.stream().map(AddressDto::getId).collect(toSet());
        assertTrue(addressIds.containsAll(Set.of(103L, 104L)));
        assertFalse(addressIds.contains(102L) && addressIds.contains(104L));
        final Set<Long> countryIds = addresses.stream().map(a -> a.getCountryDto().getId()).collect(toSet());
        assertTrue(countryIds.containsAll(Set.of(12L, 13L)));
        assertFalse(countryIds.contains(45L));

    }

    @Test
    void getUserCacheable() {
        final Long id = 103L;
        assertDoesNotThrow(() -> userService.getUser(id));
        userService.getUser(id);
        verify(userRepository, times(1)).getUserById(eq(id));
    }

    @Test
    void getUserNotFound() {
        final long id = 1000L;
        assertThrows(EntityNotFoundException.class, () -> userService.getUser(id));
    }

    @Test
    void deleteUser() {
        final long id = 104L;
        userService.getUser(id);
        assertDoesNotThrow(() -> userService.deleteUser(id));
        assertThrows(EntityNotFoundException.class, () -> userService.getUser(id));
        verify(userRepository, times(2)).getUserById(eq(id));
    }

    @Test
    void getUserForUpdate() {
        final UserModelDto userForUpdate = userService.getUserProfileForUpdate(100L);
        assertEquals(100L, userForUpdate.userDto().getId().orElseThrow());
        assertFalse(userForUpdate.allExistedCountries().isEmpty());
    }

    @Test
    void updateUserProfileNotUniqueEmail() {
        final String firstNAme = "First name";
        final String lastName = "Last Name";
        final String email = "dsfg@gmail.com";
        final UserDto dto = UserDto.builder()
                .firstname(firstNAme)
                .lastname(lastName)
                .email(email)
                .id(101L)
                .build();
        try {
            userService.validateAndUpdateUserProfile(dto);
        } catch (ValidationCustomException e) {
            final Map<String, String> messageMap = e.getMessageMap();
            assertTrue(messageMap.containsKey("User email"));
            assertEquals("user.validation.email.non.unique", messageMap.get("User email"));
        }
    }

    @Test
    void updateUserProfileNotUniqueFullName() {
        final String firstNAme = "First name";
        final String lastName = "Last Name";
        final String email = "dsfhhg@gmail.com";
        final UserDto dto = UserDto.builder()
                .firstname(firstNAme)
                .lastname(lastName)
                .email(email)
                .id(101L)
                .build();
        try {
            userService.validateAndUpdateUserProfile(dto);
        } catch (ValidationCustomException e) {
            final Map<String, String> messageMap = e.getMessageMap();
            assertTrue(messageMap.containsKey("User's first name and last name"));
            assertEquals("user.validation.full.name.non.unique", messageMap.get("User's first name and last name"));
        }
    }

    @Test
    void updateUserProfile() {
        clearInvocations(userRepository);
        final long id = 100L;
        final UserDto newUser = UserDto.builder().id(id).firstname("firstname").email("newEmail@jkhjh.com").lastname("new last name").build();
        final AddressedUserDto addressedUserDto = userService.updateUserProfile(newUser);
        final UserDto userDto = addressedUserDto.userDto();
        assertEquals(userDto.getId(), newUser.getId());
        assertEquals(userDto.getEmail(), newUser.getEmail());
        assertEquals(userDto.getLastname(), userDto.getLastname());
        userService.getUser(id);
        verify(userRepository, times(1)).getUserById(id);
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