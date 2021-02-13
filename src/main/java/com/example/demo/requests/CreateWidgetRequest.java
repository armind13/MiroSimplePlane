package com.example.demo.requests;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CreateWidgetRequest {

    @NotNull
    @Min(value = 1, message = "Height should be positive")
    private Integer height;

    @NotNull
    @Min(value = 1, message = "Width should be positive")
    private Integer width;

    @NotNull
    private Integer x;

    @NotNull
    private Integer y;

    private Integer z;

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

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
}
