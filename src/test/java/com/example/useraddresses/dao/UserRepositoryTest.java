package com.example.useraddresses.dao;

import com.example.useraddresses.domain.Address;
import com.example.useraddresses.domain.Country;
import com.example.useraddresses.domain.UserProfile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Sql({"classpath:statements/insert_user.sql",
        "classpath:statements/insert_address.sql"
})
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {
        "classpath:statements/truncate_user.sql",
        "classpath:statements/truncate_address.sql"})

class UserRepositoryTest extends BaseDataJpaTest {
    @Autowired
    private UserRepository repository;

    @Test
    @Transactional
    void firstTest() {
        final List<UserProfile> all = repository.findAll();
        all.forEach(x-> System.out.println(x.toString()));
    }

    @Test
    @Transactional
    void two() {
        final Optional<UserProfile> optionalUser = repository.findById(100L);
        final UserProfile user = optionalUser.orElseThrow();
        final Address address = new Address(user, "Bro", "Street", "21", "22", new Country(3L));
        user.addOneAddress(address);
        final UserProfile user1 = repository.saveAndFlush(user);
        System.out.println(user1.toString());

    }

    @Test
    @Transactional
    void three() {
        final Optional<UserProfile> user = repository.findById(100L);
        final UserProfile user1 = user.orElseThrow();
        assertThrows(IllegalArgumentException.class, () -> user1.addOneAddress(new Address(user1, "Kyiv", "Hreschatic", "21", "10", new Country(26L))));
    }

    @Test
    @Transactional
    void four() {
        final Optional<UserProfile> user = repository.findById(100L);
        final UserProfile user1 = user.orElseThrow();
        assertDoesNotThrow(() -> user1.addOneAddress(new Address(user1, "Bro", "Hreschatic", "21", "10", new Country(26L))));
    }

}