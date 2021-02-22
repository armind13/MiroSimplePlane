package com.example.demo.storage;

public class NotFoundException extends Exception {

    private Long id;

    public NotFoundException() {
    }

    public NotFoundException(long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        if (id != null)
            return "Id " + id + " not found";

        return "Widgets does not exist";
    }
}
