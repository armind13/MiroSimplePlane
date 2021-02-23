package com.example.demo.http.controllers;

import com.example.demo.http.requests.CreateWidgetRequest;
import com.example.demo.http.requests.UpdateWidgetRequest;
import com.example.demo.http.responses.*;
import com.example.demo.storage.IWidgetsRepository;
import com.example.demo.storage.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/widget")
@Validated
@Service
public class WidgetCrudController {

    private IWidgetsRepository repository;

    @Autowired
    public void setRepository(IWidgetsRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/create")
    public ResponseEntity<CreateWidgetResponse> create(@Valid @RequestBody CreateWidgetRequest request){

        var widget = repository.add(
                request.getX(),
                request.getY(),
                request.getWidth(),
                request.getHeight(),
                request.getZ());

        var response = new CreateWidgetResponse(
                widget.getId(),
                widget.getX(),
                widget.getY(),
                widget.getZIndex(),
                widget.getWidth(),
                widget.getHeight(),
                widget.getUpdatedDateTimeUtc());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/update")
    public ResponseEntity<UpdateWidgetResponse> update(@Valid @RequestBody UpdateWidgetRequest request) throws NotFoundException {

        var widget = repository.update(request.getId(),
                request.getX(),
                request.getY(),
                request.getWidth(),
                request.getHeight(),
                request.getZIndex());

        var response = new UpdateWidgetResponse(widget.getId(),
                widget.getX(),
                widget.getY(),
                widget.getZIndex(),
                widget.getWidth(),
                widget.getHeight(),
                widget.getUpdatedDateTimeUtc());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get")
    public ResponseEntity<GetWidgetResponse> get(@RequestParam @NotNull Long id) throws NotFoundException {

        var widget = repository.get(id);
        var response = new GetWidgetResponse(
                widget.getId(),
                widget.getX(),
                widget.getY(),
                widget.getZIndex(),
                widget.getWidth(),
                widget.getHeight(),
                widget.getUpdatedDateTimeUtc()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<GetAllWidgetsResponse> getAll() throws NotFoundException {

        var widgets = repository.getAll();
        var models = new WidgetResponseModel[widgets.length];
        for (int i = 0; i < widgets.length; i++) {
            var widget = widgets[i];
            var model = new WidgetResponseModel(widget.getId(),
                    widget.getX(),
                    widget.getY(),
                    widget.getZIndex(),
                    widget.getWidth(),
                    widget.getHeight(),
                    widget.getUpdatedDateTimeUtc());
            models[i] = model;
        }
        var response = new GetAllWidgetsResponse(models);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<DeleteWidgetResponse> delete(@RequestParam @NotNull Long id) {

        repository.delete(id);

        return ResponseEntity.ok(new DeleteWidgetResponse(id));
    }
}
