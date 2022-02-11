package com.example.useraddresses.domain;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@Configuration(proxyBeanMethods = false)
@EntityScan({"com.example.useraddresses.domain"})
@EnableJpaRepositories(basePackages = {"com.example.useraddresses.dao"})
public class DomainConfig {
}
