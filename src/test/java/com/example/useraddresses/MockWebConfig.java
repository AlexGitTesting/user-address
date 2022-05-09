package com.example.useraddresses;

import com.example.useraddresses.service.EmailService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.*;

@Configuration(proxyBeanMethods = false)
public class MockWebConfig {

    @Bean
    @Primary
    public EmailService emailService(){
        final EmailService mock = mock(EmailService.class);
        doNothing().when(mock).sendSimpleMessage(any(),any(),any());
        return mock;
    }
}
