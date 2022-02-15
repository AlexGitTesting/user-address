package com.example.useraddresses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(proxyBeanMethods = false,scanBasePackages = {"com.example.useraddresses"})
public class UserAddressesApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserAddressesApplication.class, args);
    }

}
