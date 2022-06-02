package com.example.useraddresses.service;

import com.example.useraddresses.core.ValidationCustomException;
import com.example.useraddresses.dto.AddressDto;

import javax.persistence.EntityNotFoundException;
import java.util.Set;

/**
 * Contract for service implementation to interact with address.
 *
 * @author Alexandr Yefremov
 */
public interface AddressService {
    /**
     * Creates new address for specified user
     *
     * @param addresses new address
     * @param userId    specified user's id
     * @return address's ids
     * @throws ValidationCustomException if incoming params do not match with condition
     * @throws EntityNotFoundException   if user is not exist with specified id
     */
    Set<Long> createAddress(Set<AddressDto> addresses, Long userId) throws ValidationCustomException, EntityNotFoundException;

    /**
     * Removes several addresses if they belong to the specified user
     *
     * @param addressIds addresses will be  removed
     * @param userId     owner of this addresses
     * @return removed address's ids
     * @throws IllegalStateException if incoming address's ids do not belong to the specified user
     */
    Set<Long> deleteAddress(Set<Long> addressIds, Long userId) throws IllegalStateException;

    /**
     * Updates several addresses which belong to the specified user
     *
     * @param addresses addresses will be updated
     * @param userId    owner of this addresses
     * @return updated addresses
     * @throws ValidationCustomException if candidates do not match with conditions
     * @throws IllegalStateException     if addresses do not belong to specified user
     */
    Set<AddressDto> updateAddress(Set<AddressDto> addresses, Long userId) throws ValidationCustomException, IllegalStateException;
}
