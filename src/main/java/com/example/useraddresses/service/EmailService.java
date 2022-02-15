package com.example.useraddresses.service;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
}
