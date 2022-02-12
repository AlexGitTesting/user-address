package com.example.useraddresses.dao;

import com.example.useraddresses.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        final List<User> all = repository.findAll();
        all.forEach(x-> System.out.println(x.toString()));
    }

}