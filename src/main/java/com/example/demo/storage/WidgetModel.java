package com.example.demo.storage;

import java.time.OffsetDateTime;

public class WidgetModel implements ILayerUpdater {
    private long id;
    private int x;
    private int y;
    private int zIndex;
    private int width;
    private int height;
    private OffsetDateTime updatedDateTimeUtc;

    public WidgetModel(long id, int x, int y, int width, int height, OffsetDateTime updatedDateTimeUtc) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.updatedDateTimeUtc = updatedDateTimeUtc;
    }

    public long getId() {
        return id;
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

    public int getZIndex() {
        return zIndex;
    }

    public void setZIndex(int zIndex) {
        this.zIndex = zIndex;
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
