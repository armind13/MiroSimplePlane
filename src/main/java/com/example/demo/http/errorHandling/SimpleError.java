package com.example.demo.http.errorHandling;

public class SimpleError {
    private String message;
    private String field;
    private Object rejectedValue;

    public SimpleError(String message, String field, Object rejectedValue) {
        this.message = message;
        this.field = field;
        this.rejectedValue = rejectedValue;
    }

    public String getMessage() {
        return message;
    }

    public String getField() {
        return field;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }
}
