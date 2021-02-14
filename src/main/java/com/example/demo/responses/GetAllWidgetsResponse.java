package com.example.demo.responses;

public class GetAllWidgetsResponse {

    private WidgetResponseModel[] widgets;

    public GetAllWidgetsResponse(WidgetResponseModel[] widgets) {
        this.widgets = widgets;
    }

    public WidgetResponseModel[] getWidgets() {
        return widgets;
    }

    public void setWidgets(WidgetResponseModel[] widgets) {
        this.widgets = widgets;
    }
}
