package com.example.demo.exception;

import lombok.Data;

@Data
public class MyCustomException {
    private final String message;
    private final String exceptionName;
}
