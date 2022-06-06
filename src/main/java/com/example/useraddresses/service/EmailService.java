package com.example.useraddresses.service;

/**
 * Service for sending emails.
 *
 * @author Alexandr Yefremov
 */
public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
}
