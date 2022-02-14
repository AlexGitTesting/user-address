package com.example.useraddresses.service;

import com.example.useraddresses.core.RequiredFieldsForCreation;
import com.example.useraddresses.core.RequiredFieldsForUpdating;
import com.example.useraddresses.core.ValidationCustomException;
import com.example.useraddresses.dao.AddressRepository;
import com.example.useraddresses.domain.Address;
import com.example.useraddresses.domain.User;
import com.example.useraddresses.dto.AddressDto;
import com.example.useraddresses.service.converter.AddressConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.groups.Default;
import java.util.*;

import static java.util.stream.Collectors.*;

@Service
// TODO: 14.02.2022 fill 
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final ValidationService validationService;
    private final AddressConverter addressConverter;

    @Autowired
    public AddressServiceImpl(final AddressRepository addressRepository,
                              final ValidationService validationService,
                              final AddressConverter addressConverter) {
        this.addressRepository = addressRepository;
        this.validationService = validationService;
        this.addressConverter = addressConverter;
    }

    @Override
    @Transactional
    public List<AddressDto> createAddress(List<AddressDto> dto, Long userId) throws ValidationCustomException {
        // TODO: 14.02.2022 check if user exists validating if name

        if (!(userId == null || userId < 1))
            throw new IllegalArgumentException("user is not new"); // TODO: 14.02.2022   validate into controller
        verifyAddressesGroup(dto, RequiredFieldsForCreation.class);
        final User user = new User();
        user.setId(userId);
        final List<Address> addresses = dto.stream().map(addressConverter::convertToDomain).peek(address -> address.setUser(user)).collect(toList());
        final List<Address> savedAddresses = addressRepository.saveAll(addresses);
        return savedAddresses.stream().map(addressConverter::convertToDto).collect(toList());
    }

    @Override
    @Transactional
    public Set<Long> deleteAddress(Set<Long> addressIds, Long userId) { // TODO: 14.02.2022  check into controller ids
        final Set<Long> addressIdByUserId = addressRepository.getAddressIdsByUserId(userId);
        if (!addressIdByUserId.containsAll(addressIds))
            throw new IllegalStateException("user.validation.wrong.address.ids");
        addressRepository.deleteAllById(addressIds);
        return addressIds;
    }

    @Override
    @Transactional
    public Set<AddressDto> updateAddress(Set<AddressDto> addresses, Long userId) throws ValidationCustomException { // TODO: 14.02.2022 verify ids in controller
        verifyAddressesGroup(addresses, RequiredFieldsForUpdating.class);
        final Set<Long> dtoIds = addresses.stream().map(AddressDto::getId).collect(toSet());
        final Set<Address> userAddressesByIdIn = addressRepository.findAddressesByUserIdIn(dtoIds, userId);
        final Map<Long, Set<Address>> groupedAddresses = userAddressesByIdIn
                .stream()
                .collect(groupingBy(Address::getId, toSet()));
        if (groupedAddresses.isEmpty() || !(groupedAddresses.keySet()).containsAll(dtoIds)) {
            throw new IllegalStateException("user.validation.wrong.address.ids");
        }
        addresses.forEach(addressDto -> {
            final Set<Address> addressSet = groupedAddresses.get(addressDto.getId());
            if (addressSet.size() != 1)
                throw new IllegalStateException("Internal server error in " + this.getClass().getSimpleName());
            final Address currentAddress = groupedAddresses.get(addressDto.getId()).stream().findFirst().orElseThrow();
            addressConverter.convertToDomainTarget(addressDto, currentAddress);
        });
       return  addressRepository.saveAll(userAddressesByIdIn).stream().map(addressConverter::convertToDto).collect(toSet());
    }

    private void verifyAddressesGroup(Collection<AddressDto> addresses, Class<? extends Default> group) {
        final Map<String, String> messageMap = new HashMap<>();
        addresses.forEach(addressDto -> {
            try {
                validationService.validate(addressDto, "AddressDto", group);
            } catch (ValidationCustomException e) {
                final Map<String, String> eMessageMap = e.getMessageMap();
                eMessageMap.forEach(messageMap::putIfAbsent);
            }
        });
        if (!messageMap.isEmpty()) {
            throw new ValidationCustomException(messageMap);
        }
    }
}
