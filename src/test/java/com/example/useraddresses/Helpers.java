package com.example.useraddresses;

import com.example.useraddresses.domain.Address;
import com.example.useraddresses.domain.Country;
import com.example.useraddresses.domain.User;

import java.time.LocalDateTime;

// TODO: 31.05.2022 check if used
public interface Helpers {
    static Country createCountry(Long id, LocalDateTime created, LocalDateTime modified, String name){
        return new Country(id, created,modified,name);
    }

    static Address createAddress(User user, String city, String street, String houseNumber, String flatNumber, Country country){
        return new Address(user,city,street,houseNumber,flatNumber,country);
    }
}
