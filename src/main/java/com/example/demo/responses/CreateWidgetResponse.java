package com.example.demo.responses;

public class CreateWidgetResponse {

    private int x;
    private int y;
    private int z;
    private int height;
    private int width;

    public CreateWidgetResponse(int x, int y, int z, int height, int width) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.height = height;
        this.width = width;
    }

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

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
