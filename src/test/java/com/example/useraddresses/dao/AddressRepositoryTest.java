package com.example.useraddresses.dao;

import com.example.useraddresses.domain.Address;
import com.example.useraddresses.domain.BaseEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Sql({"classpath:statements/insert_user.sql",
        "classpath:statements/insert_address.sql"
})
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {
        "classpath:statements/truncate_user.sql",
        "classpath:statements/truncate_address.sql"})
@SpringBootTest
class AddressRepositoryTest {
    @Autowired
    private AddressRepository repository;

    @Test
    void getAddressIdByUserId() {
        final Set<Long> addressIdsByUserId = repository.getAddressIdsByUserId(100L);
        assertTrue(addressIdsByUserId.containsAll(Set.of(100L, 101L)));
        assertFalse(addressIdsByUserId.contains(102L));
    }

    @Test
    void findAddressesByUserIdIn() {
        final Set<Long> addressIds = Set.of(100L);
        final Set<Address> addressesByUserIdIn = repository.findAddressesByUserIdIn(addressIds, 100L);
        assertTrue(addressIds.containsAll(addressesByUserIdIn.stream().map(BaseEntity::getId).collect(Collectors.toSet())));
    }
}