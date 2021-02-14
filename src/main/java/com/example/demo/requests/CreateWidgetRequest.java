package com.example.demo.requests;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CreateWidgetRequest {

    @NotNull
    private Integer x;

    @NotNull
    private Integer y;

    private Integer z;

    @NotNull
    @Min(value = 1, message = "Width should be positive")
    private Integer width;

    @NotNull
    @Min(value = 1, message = "Height should be positive")
    private Integer height;

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getZ() {
        return z;
    }

    public void setZ(Integer z) {
        this.z = z;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }
}
