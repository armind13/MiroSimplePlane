package com.example.demo.http.responses;

import java.time.OffsetDateTime;

public class WidgetResponseModel {

    private long id;
    private int x;
    private int y;
    private int z;
    private int width;
    private int height;
    private OffsetDateTime updatedDateTimeUtc;

    public WidgetResponseModel(long id, int x, int y, int z, int width, int height, OffsetDateTime updatedDateTimeUtc) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
        this.width = width;
        this.height = height;
        this.updatedDateTimeUtc = updatedDateTimeUtc;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public OffsetDateTime getUpdatedDateTimeUtc() {
        return updatedDateTimeUtc;
    }

    public void setUpdatedDateTimeUtc(OffsetDateTime updatedDateTimeUtc) {
        this.updatedDateTimeUtc = updatedDateTimeUtc;
    }
}
