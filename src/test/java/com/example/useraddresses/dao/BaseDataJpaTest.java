package com.example.useraddresses.dao;

import com.example.useraddresses.PostgresSqlContainerInitializer;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 *
 */
// TODO: 11.02.2022 fil the explanation
@DataJpaTest
@EnableJpaAuditing
//@EntityScan({"com.example.useraddresses.domain"})
//@EnableJpaRepositories(basePackages = {"com.example.useraddresses.dao"})
public abstract class BaseDataJpaTest extends PostgresSqlContainerInitializer {
}
