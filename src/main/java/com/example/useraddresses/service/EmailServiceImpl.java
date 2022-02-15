package com.example.useraddresses.service;

import com.example.useraddresses.config.MailProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Resource;
import java.util.Objects;

import static java.util.Objects.*;

@Service
// TODO: 15.02.2022 fill
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    private final MailProperties mailProperties;
    private final MessageSource messageSource;

    @Resource
    private WebRequest webRequest;

    @Autowired
    public EmailServiceImpl(final JavaMailSender mailSender, final MailProperties mailProperties, final MessageSource messageSource) {
        this.mailSender = mailSender;
        this.mailProperties = mailProperties;
        this.messageSource = messageSource;
    }

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailProperties.getHost());
        message.setTo(to);
        message.setSubject(requireNonNullElse(messageSource.getMessage(subject, null
                , webRequest.getLocale()), "Congratulations, you have successfully registered on the portal \"Address Storage\""));
        message.setText(requireNonNullElse(messageSource.getMessage(text, null
                , webRequest.getLocale()), "Now you can feel the full power of its functionality"));
        mailSender.send(message);
    }

}
