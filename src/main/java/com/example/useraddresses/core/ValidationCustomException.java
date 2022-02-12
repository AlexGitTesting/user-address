package com.example.useraddresses.core;

import java.util.Map;

// TODO: 12.02.2022 fill
public class ValidationCustomException extends RuntimeException{
    private final static String standardMessage = "During  validation error occurred!";
    private Map<String, String> messageMap;

    public ValidationCustomException() {
    }

    public ValidationCustomException( Map<String, String> messageMap) {
        super(standardMessage);
        this.messageMap = messageMap;
    }

    public Map<String, String> getMessageMap() {
        return messageMap;
    }
}
