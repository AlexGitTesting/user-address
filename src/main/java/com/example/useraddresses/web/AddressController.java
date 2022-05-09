package com.example.useraddresses.web;

import com.example.useraddresses.dto.AddressDto;
import com.example.useraddresses.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/address", produces = APPLICATION_JSON_VALUE)
public class AddressController {
    private final AddressService service;

    @Autowired
    public AddressController(final AddressService service) {
        this.service = service;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/{id}/create.json", consumes = MediaType.APPLICATION_JSON_VALUE)
    List<AddressDto> createAddress(@RequestBody List<AddressDto> addresses, @PathVariable("id") final Long userId) {
        // TODO: 17.02.2022 may be validate with beanvalidation
        if (addresses==null||addresses.isEmpty()||userId == null || userId < 1) throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid argument");
        return service.createAddress(addresses, userId);

    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}/delete.json")
    Set<Long> deleteAddress(@RequestBody Set<Long> addressIds, @PathVariable("id") final Long userId) {
        if (addressIds==null||addressIds.isEmpty()||userId == null || userId < 1) throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid argument");
        return service.deleteAddress(addressIds, userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}/update.json", consumes = MediaType.APPLICATION_JSON_VALUE)
    Set<AddressDto> updateAddress(@RequestBody Set<AddressDto> addresses, @PathVariable("id") final Long userId) {
        if (addresses==null||addresses.isEmpty()||userId == null || userId < 1) throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid argument");
        return service.updateAddress(addresses, userId);
    }
}
