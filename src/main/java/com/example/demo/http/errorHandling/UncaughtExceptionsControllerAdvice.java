package com.example.demo.http.errorHandling;

import com.example.demo.storage.NotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class UncaughtExceptionsControllerAdvice {

    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseError methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        var result = ex.getBindingResult();
        var fieldErrors = result.getFieldErrors();
        return processFieldErrors(fieldErrors);
    }

    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseError methodParameterNotFoundException(MissingServletRequestParameterException ex) {

        var fieldError = new FieldError("parameter", ex.getParameterName(), ex.getMessage());
        var errorList = new ArrayList<FieldError>();
        errorList.add(fieldError);

        return processFieldErrors(errorList);
    }

    @ResponseStatus(NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    public ResponseError idNotFound(NotFoundException ex) {
        var error = new ResponseError(NOT_FOUND.value(), "not found");

        var isIdExists = ex.getId() != null;
        var message = isIdExists ? ex.toString() : "Does not any widgets exist";
        var value = isIdExists ? ex.getId() : null;

        error.addFieldError("id", message, value);

        return error;
    }

    private ResponseError processFieldErrors(List<FieldError> fieldErrors) {
        var error = new ResponseError(BAD_REQUEST.value(), "validation error");

        for (var fieldError: fieldErrors) {
            error.addFieldError(fieldError.getField(), fieldError.getDefaultMessage(), fieldError.getRejectedValue());
        }

        return error;
    }
}

