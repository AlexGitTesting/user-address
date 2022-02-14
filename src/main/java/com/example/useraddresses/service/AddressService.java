package com.example.useraddresses.service;

import com.example.useraddresses.core.ValidationCustomException;
import com.example.useraddresses.dto.AddressDto;

import java.util.List;
import java.util.Set;

public interface AddressService {
    List<AddressDto> createAddress(List<AddressDto> addresses, Long userId)throws ValidationCustomException;
    Set<Long> deleteAddress(Set<Long> addressIds, Long UserId);
    Set<AddressDto> updateAddress(Set<AddressDto> addresses, Long userId) throws ValidationCustomException;
}
