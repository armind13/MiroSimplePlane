package com.example.demo.http.controllers;

import com.example.demo.http.requests.CreateWidgetRequest;
import com.example.demo.http.requests.UpdateWidgetRequest;
import com.example.demo.http.responses.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@RestController
@RequestMapping("/widget")
@Validated
public class WidgetCrudController {

    @PostMapping("/create")
    public ResponseEntity<CreateWidgetResponse> create(@Valid @RequestBody CreateWidgetRequest request){

        var response = new CreateWidgetResponse(
                1, request.getX(),
                request.getY(),
                request.getZ() == null ? Integer.MAX_VALUE : request.getZ().intValue(),
                request.getWidth(),
                request.getHeight(),
                OffsetDateTime.now(ZoneOffset.UTC));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/update")
    public ResponseEntity<UpdateWidgetResponse> update(@Valid @RequestBody UpdateWidgetRequest request){

        //not found

        var response =  new UpdateWidgetResponse(
                request.getId().longValue(),
                request.getX() != null ? request.getX().intValue() : 0,
                request.getY() != null ? request.getY().intValue() : 0,
                request.getZ() != null ? request.getZ().intValue() : 0,
                request.getWidth() != null ? request.getWidth().intValue() : 1,
                request.getHeight() != null ? request.getHeight().intValue() : 1,
                OffsetDateTime.now(ZoneOffset.UTC));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get")
    public ResponseEntity<GetWidgetResponse> get(@RequestParam @NotNull Long id){

        //not found

        var response =  new GetWidgetResponse(id,
                0,
                0,
                1,
                1,
                1,
                OffsetDateTime.now(ZoneOffset.UTC));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<GetAllWidgetsResponse> getAll(){

        var widget = new WidgetResponseModel(
                0,
                1,
                2,
                3,
                4,
                5,
                OffsetDateTime.now(ZoneOffset.UTC));
        var widgets = new WidgetResponseModel[]{widget, widget};
        var response = new GetAllWidgetsResponse(widgets);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Long> delete(@RequestParam @NotNull Long id){

        //not found

        return ResponseEntity.ok(id);
    }
}
