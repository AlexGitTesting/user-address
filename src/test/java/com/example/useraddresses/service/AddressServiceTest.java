package com.example.useraddresses.service;

import com.example.useraddresses.core.ValidationCustomException;
import com.example.useraddresses.dao.AddressRepository;
import com.example.useraddresses.dto.AddressDto;
import com.example.useraddresses.dto.AddressedUserDto;
import com.example.useraddresses.dto.CountryDto;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@Sql({"classpath:statements/insert_user.sql",
        "classpath:statements/insert_address.sql"
})
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {
        "classpath:statements/truncate_user.sql",
        "classpath:statements/truncate_address.sql"})
@SpringBootTest
class AddressServiceTest {

    @SpyBean
    private UserService userService;
    @SpyBean
    private ValidationService validationService;
    @SpyBean
    private AddressRepository addressRepository;
    @Autowired
    private AddressService addressService;

    @Test
//    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD) todo clean
    void createAddress() {
        final CountryDto countryDto = CountryDto.builder().id(5L).name("kjshglkhg").build();
        final AddressDto addressDto = AddressDto.builder()
                .id(null)
                .flatNumber("12")
                .houseNumber("12")
                .street("kklhh")
                .city("city")
                .countryDto(countryDto)
                .build();
        assertDoesNotThrow(() -> addressService.createAddress(List.of(addressDto), 100L));
    }

    @Test
    void createAddressUserNotFound() {
        final CountryDto countryDto = CountryDto.builder().id(5L).name("kjshglkhg").build();
        final AddressDto addressDto = AddressDto.builder()
                .id(null)
                .flatNumber("12")
                .houseNumber("12")
                .street("kklhh")
                .city("city")
                .countryDto(countryDto)
                .build();
        assertThrowsExactly(EntityNotFoundException.class, () -> addressService.createAddress(List.of(addressDto), 11L));
    }

    @Test
    void createAddressUserNotFoundByMockito() {
        doThrow(EntityNotFoundException.class).when(userService).ifUserExists(eq(1000L));
        doNothing().when(validationService).validate(any(), anyString(), any());
        final CountryDto countryDto = CountryDto.builder().id(5L).name("kjshglkhg").build();
        final AddressDto addressDto = AddressDto.builder()
                .id(null)
                .flatNumber("12")
                .houseNumber("12")
                .street("kklhh")
                .city("city")
                .countryDto(countryDto)
                .build();
        assertThrows(EntityNotFoundException.class, () -> addressService.createAddress(List.of(addressDto), 1000L));
        verify(validationService, atLeastOnce()).validate(any(), anyString(), any());

    }

    @Test
    void createAddressFailedValidation() {
        final CountryDto countryDto = CountryDto.builder().id(5L).name("kjshglkhg").build();
        final AddressDto addressDto = AddressDto.builder()
                .id(null)
                .flatNumber("12")
                .houseNumber("12")
                .street("kklhh")
                .city("city")
                .countryDto(countryDto)
                .build();
        doThrow(new ValidationCustomException(Collections.singletonMap("key", "value"))).when(validationService).validate(any(), anyString(), any());
        assertThrows(ValidationCustomException.class, () -> addressService.createAddress(List.of(addressDto), 1000L));
    }

    @Test
    void deleteAddress() {
        final Set<Long> addressIds = Set.of(100L);
        final long userId = 100L;
        final Set<Long> deletedIds = addressService.deleteAddress(addressIds, userId);
        assertTrue(addressIds.containsAll(deletedIds));
        assertEquals(addressIds.size(), deletedIds.size());
        final AddressedUserDto userDto = userService.getUser(userId);
        assertNotNull(userDto);
        final Set<Long> existedAddressIds = userDto.addresses().stream().map(AddressDto::getId).collect(Collectors.toSet());
        assertFalse(existedAddressIds.isEmpty());
        addressIds.forEach(id -> assertFalse(existedAddressIds.contains(id)));
    }

    @Test
    void deleteAddressNotBelongToUser() {
        final Set<Long> addressIds = Set.of(100L, 102L);
        assertThrowsExactly(IllegalStateException.class, () -> addressService.deleteAddress(addressIds, 100L));
        verify(addressRepository, never()).deleteAllById(addressIds);
    }

    @Test
    void updateAddressBelongToUser() {
        final Set<AddressDto> addressDtos = prepareAddressDtos();
        final long userId = 100L;
        final Set<AddressDto> updatedAddresses = addressService.updateAddress(addressDtos, userId);
        assertEquals(addressDtos.size(), updatedAddresses.size());
        addressDtos.forEach(dto -> verifyAddressDto(dto, updatedAddresses));
        verify(addressRepository, atLeastOnce()).findAddressesByUserIdIn(addressDtos.stream().map(AddressDto::getId).collect(Collectors.toSet()), userId);
        final ArgumentMatcher<Set<Long>> matcher = argument -> argument.containsAll(addressDtos.stream().map(AddressDto::getId).collect(Collectors.toSet()));
        verify(addressRepository, atLeastOnce()).findAddressesByUserIdIn(argThat(matcher), eq(userId));
    }


    private void verifyAddressDto(AddressDto oldAddress, Set<AddressDto> updatedAddresses) {
        final AddressDto updatedAddress = updatedAddresses.stream().filter(dto -> dto.getId().equals(oldAddress.getId())).findFirst().orElseThrow();
        assertEquals(oldAddress.getFlatNumber(), updatedAddress.getFlatNumber());
        assertEquals(oldAddress.getHouseNumber(), updatedAddress.getHouseNumber());
        assertEquals(oldAddress.getStreet(), updatedAddress.getStreet());
        assertEquals(oldAddress.getCity(), updatedAddress.getCity());
        assertEquals(oldAddress.getCountryDto().getId(), updatedAddress.getCountryDto().getId());
    }

    @Test
    void updateAddressValidationException() {
        doThrow(new ValidationCustomException(Collections.singletonMap("key", "value"))).when(validationService).validate(any(), anyString(), any());
        final Set<AddressDto> addressDtos = prepareAddressDtos();
        assertThrows(ValidationCustomException.class, () -> addressService.updateAddress(addressDtos, 100L));
        verify(addressRepository, never()).findAddressesByUserIdIn(anySet(), anyLong());
    }

    @NotNull
    private Set<AddressDto> prepareAddressDtos() {
        final CountryDto countryDto = CountryDto.builder().id(5L).name("updatedNameCountry").build();
        final AddressDto oldAddressDto = AddressDto.builder()
                .id(100L)
                .flatNumber("updated12")
                .houseNumber("updated12")
                .street("updatedNameStreet")
                .city("updatedName City")
                .countryDto(countryDto)
                .build();
        final CountryDto countryDto1 = CountryDto.builder().id(7L).name("updatedNameCountry1").build();
        final AddressDto addressDto1 = AddressDto.builder()
                .id(101L)
                .flatNumber("updated56")
                .houseNumber("updated1")
                .street("updatedNameStreet1")
                .city("updatedName City1")
                .countryDto(countryDto1)
                .build();
        return Set.of(oldAddressDto, addressDto1);
    }
}