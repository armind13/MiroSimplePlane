package com.example.demo.requests;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CreateWidgetRequest {

    private int x;

    private int y;

    private Integer z;

    @NotNull
    @Min(value = 1, message = "Height should be positive")
    private Integer height;

    @Min(value = 1, message = "Width should be positive")
    private int width;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Integer getZ() {
        return z;
    }

    public void setZ(Integer z) {
        this.z = z;
    }

    public @Min(value = 1, message = "Height should be positive") Integer getHeight() {
        return height;
    }

    public void setHeight(@Min(value = 1, message = "Height should be positive") Integer height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
