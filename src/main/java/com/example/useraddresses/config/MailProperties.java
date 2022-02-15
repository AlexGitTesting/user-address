package com.example.useraddresses.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "spring.mail")
public class MailProperties {
    private final String host;

    public MailProperties(final String host) {
        this.host = host;
    }

    public String getHost() {
        return host;
    }
}
