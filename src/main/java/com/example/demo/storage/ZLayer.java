package com.example.demo.storage;

public class ZLayer {
    private int z;

    private WidgetModel widget;

    public ZLayer(int z, WidgetModel widget) {
        this.z = z;
        this.widget = widget;
    }

    public int getZ() {
        return z;
    }

    public WidgetModel getWidget() {
        return widget;
    }

    public void setWidget(WidgetModel widget) {
        this.widget = widget;
    }
}
