package com.example.demo.storage;

import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class InMemoryWidgetsRepository implements IWidgetsRepository {

    private IDateTimeProvider dateTimeProvider;

    private LayerStorage layerStorage;

    private HashMap<Long, WidgetModel> widgets;

    private long id = 1;

    public InMemoryWidgetsRepository(IDateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
        layerStorage = new LayerStorage();
        widgets = new HashMap<>();
    }

    public WidgetModel add(int x, int y, int width, int height, Integer zIndex) {
        var utcNow = dateTimeProvider.GetNow();
        var currentId = this.id++;
        var widget = new WidgetModel(currentId, x, y, width, height, utcNow);

        if (zIndex == null)
            zIndex = layerStorage.getMaxZIndex() + 1;

        widget.setZIndex(zIndex);

        layerStorage.add(widget, zIndex);
        widgets.put(currentId, widget);

        return widget;
    }

    public WidgetModel update(long id, Integer x, Integer y, Integer width, Integer height, Integer zIndex) throws NotFoundException {
        ThrowIfNotExists(id);

        var updateTime = dateTimeProvider.GetNow();
        var widget= widgets.get(id);

        if (x != null)
            widget.setX(x);
        if (y != null)
            widget.setY(y);
        if (width != null)
            widget.setWidth(width);
        if (height != null)
            widget.setHeight(height);
        if (zIndex != null)
            layerStorage.update(widget.getZIndex(), zIndex);

        widget.setUpdatedDateTimeUtc(updateTime);

        return widget;
    }

    public WidgetModel get(long id) throws NotFoundException {
        ThrowIfNotExists(id);

        return widgets.get(id);
    }

    public WidgetModel[] getAll() throws NotFoundException {

        var ids = layerStorage.getOrderedId();
        if (ids.length == 0)
            throw new NotFoundException();

        var allWidgets = new WidgetModel[ids.length];
        for (var i = 0; i < ids.length; i++) {
            allWidgets[i] = widgets.get(ids[i]);
        }

        return allWidgets;
    }

    public void delete(long id) throws NotFoundException {
        ThrowIfNotExists(id);

        var widget = widgets.get(id);
        layerStorage.delete(widget.getZIndex());
        widgets.remove(id);
    }

    private void ThrowIfNotExists(long id) throws NotFoundException {
        if (!widgets.containsKey(id))
            throw new NotFoundException(id);
    }
}

