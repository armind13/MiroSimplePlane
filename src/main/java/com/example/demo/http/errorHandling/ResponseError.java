package com.example.demo.http.errorHandling;

import java.util.ArrayList;
import java.util.List;

public class ResponseError {
    private final int status;
    private final String message;
    private List<SimpleError> errors = new ArrayList<>();

    ResponseError(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void addFieldError(String field, String message, Object rejectedValue) {
        var error = new SimpleError(message, field, rejectedValue);
        errors.add(error);
    }

    public List<SimpleError> getErrors() {
        return errors;
    }
}

