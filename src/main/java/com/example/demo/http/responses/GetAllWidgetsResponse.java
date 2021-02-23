package com.example.demo.http.responses;

public class GetAllWidgetsResponse {

    private WidgetResponseModel[] widgets;

    public GetAllWidgetsResponse() {
        this.widgets = null;
    }

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
