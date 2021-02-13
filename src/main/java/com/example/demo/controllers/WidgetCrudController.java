package com.example.demo.controllers;

import com.example.demo.requests.CreateWidgetRequest;
import com.example.demo.responses.CreateWidgetResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/widget")
@Validated
public class WidgetCrudController {

    @PostMapping("/create")
    public CreateWidgetResponse create(@Valid @RequestBody CreateWidgetRequest request){
        return new CreateWidgetResponse(
                request.getX(),
                request.getY(),
                request.getZ() == null ? Integer.MAX_VALUE : request.getZ().intValue(),
                request.getHeight(),
                request.getWidth());
    }
}
