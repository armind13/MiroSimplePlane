package com.example.demo.validators;

import com.example.demo.requests.CreateWidgetRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class CreateWidgetRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return CreateWidgetRequest.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CreateWidgetRequest request = (CreateWidgetRequest) o;

        if (request.getWidth() <= 0) {
            errors.rejectValue("width", "value.negative");
        }
    }
}
