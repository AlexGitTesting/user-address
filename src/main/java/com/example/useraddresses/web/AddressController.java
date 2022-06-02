package com.example.useraddresses.web;

import com.example.useraddresses.dto.AddressDto;
import com.example.useraddresses.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Handles HTTP requests connected with address.
 *
 * @author Alexandr Yefremov
 */
@RestController
@RequestMapping(path = "/address", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Address controller", description = "Handles HTTP requests connected with address")
public class AddressController {
    private final AddressService service;

    @Autowired
    public AddressController(final AddressService service) {
        this.service = service;
    }

    @Operation(summary = "Creates new address for specified user")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/{id}/create.json", consumes = MediaType.APPLICATION_JSON_VALUE)
    Set<Long> createAddress(@RequestBody Set<AddressDto> addresses, @PathVariable("id") final Long userId) {
        validateParams(addresses, userId);
        return service.createAddress(addresses, userId);

    }

    @Operation(summary = "Deletes addresses for specified user")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}/delete.json")
    Set<Long> deleteAddress(@RequestBody Set<Long> addressIds, @PathVariable("id") final Long userId) {
        validateParams(addressIds, userId);
        return service.deleteAddress(addressIds, userId);
    }

    @Operation(summary = "Update address for specified user")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}/update.json", consumes = MediaType.APPLICATION_JSON_VALUE)
    Set<AddressDto> updateAddress(@RequestBody Set<AddressDto> addresses, @PathVariable("id") final Long userId) {
        validateParams(addresses, userId);
        return service.updateAddress(addresses, userId);
    }

    private void validateParams(Set<?> addresses, Long userId) {
        if (addresses == null || addresses.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Collection of addresses must be not null and not empty");
        if (userId == null || userId < 1)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User's id must be nut null and gritter then 0");
    }
}
