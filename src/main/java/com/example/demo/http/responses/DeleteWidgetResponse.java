package com.example.demo.http.responses;

public class DeleteWidgetResponse {

    private long id;

    public DeleteWidgetResponse(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
