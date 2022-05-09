package com.example.useraddresses.service;

import com.example.useraddresses.core.ValidationCustomException;
import com.example.useraddresses.dto.AddressDto;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

public interface AddressService {
    List<AddressDto> createAddress(List<AddressDto> addresses, Long userId)throws ValidationCustomException, EntityNotFoundException;
    Set<Long> deleteAddress(Set<Long> addressIds, Long userId)throws IllegalStateException;
    Set<AddressDto> updateAddress(Set<AddressDto> addresses, Long userId) throws ValidationCustomException,IllegalStateException;
}
