package com.example.useraddresses.dao;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 *
 */
// TODO: 11.02.2022 fil the explanation
@DataJpaTest
@EnableJpaAuditing
//@EntityScan({"com.example.useraddresses.domain"})
//@EnableJpaRepositories(basePackages = {"com.example.useraddresses.dao"})
public abstract class BaseDataJpaTest  {
}
