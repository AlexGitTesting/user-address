package com.example.useraddresses.service;

import com.example.useraddresses.dto.AddressDto;
import com.example.useraddresses.dto.CountryDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@Sql({"classpath:statements/insert_user.sql",
        "classpath:statements/insert_address.sql"
})
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {
        "classpath:statements/truncate_user.sql",
        "classpath:statements/truncate_address.sql"})
@SpringBootTest
class AddressServiceTest {
    @Autowired
    private AddressService addressService;

    @Test
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
        final List<AddressDto> address = addressService.createAddress(List.of(addressDto), 100L);
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
        assertThrowsExactly(EntityNotFoundException.class,()->addressService.createAddress(List.of(addressDto), 11L));
    }

    @Test
    void deleteAddress() {
        final Set<Long> addressIds = Set.of(100L);
        final Set<Long> deletedIds = addressService.deleteAddress(addressIds, 100L);
        assertTrue(addressIds.containsAll(deletedIds));
    }

    @Test
    void deleteAddressBelongToUser() {
        final Set<Long> addressIds = Set.of(100L);
        final Set<Long> deletedIds = addressService.deleteAddress(addressIds, 100L);
        assertTrue(()->addressIds.containsAll(deletedIds));
    }
    @Test
    void deleteAddressNotBelongToUser() {
        final Set<Long> addressIds = Set.of(100L,102L);
        assertThrowsExactly(IllegalStateException.class,()->addressService.deleteAddress(addressIds, 100L));
    }

    @Test
    void updateAddressBelongToUser() {
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
        final Set<AddressDto> addressDtos = Set.of(oldAddressDto, addressDto1);
        final Set<AddressDto> updatedAddresses = addressService.updateAddress(addressDtos, 100L);
        assertEquals(addressDtos.size(), updatedAddresses.size());

        addressDtos.forEach(dto->verifyAddressDto( dto, updatedAddresses));
    }

    private void verifyAddressDto(AddressDto oldAddress, Set<AddressDto> updatedAddresses) {
        final AddressDto updatedAddress = updatedAddresses.stream().filter(dto -> dto.getId().equals(oldAddress.getId())).findFirst().orElseThrow();
        assertEquals(oldAddress.getFlatNumber() ,updatedAddress.getFlatNumber());
        assertEquals(oldAddress.getHouseNumber() ,updatedAddress.getHouseNumber());
        assertEquals(oldAddress.getStreet() ,updatedAddress.getStreet());
        assertEquals(oldAddress.getCity() ,updatedAddress.getCity());
        assertEquals(oldAddress.getCountryDto().getId() ,updatedAddress.getCountryDto().getId());
    }
}