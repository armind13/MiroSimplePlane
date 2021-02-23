package com.example.demo.storage.inmemory;

import com.example.demo.storage.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class InMemoryWidgetsRepository implements IWidgetsRepository {

    private IDateTimeProvider dateTimeProvider;

    private LayerStorage layerStorage;

    private HashMap<Long, WidgetModel> widgets;

    private long id = 1;

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public InMemoryWidgetsRepository(IDateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
        layerStorage = new LayerStorage();
        widgets = new HashMap<>();
    }

    public WidgetModel add(int x, int y, Integer zIndex, int width, int height) {
        var utcNow = dateTimeProvider.GetNow();

        lock.writeLock().lock();
        try {
            var currentId = this.id++;
            var widget = new WidgetModel(currentId, x, y, width, height, utcNow);

            if (zIndex == null)
                zIndex = layerStorage.getMaxZIndex() + 1;

            widget.setZIndex(zIndex);

            layerStorage.add(widget, zIndex);
            widgets.put(currentId, widget);

            return widget;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public WidgetModel update(long id, Integer x, Integer y, Integer zIndex, Integer width, Integer height) throws NotFoundException {
        var updateTime = dateTimeProvider.GetNow();

        lock.writeLock().lock();
        try {
            ThrowIfNotExists(id);

            var widget = widgets.get(id);
            if (x != null)
                widget.setX(x);
            if (y != null)
                widget.setY(y);
            if (zIndex != null)
                layerStorage.update(widget.getZIndex(), zIndex);
            if (width != null)
                widget.setWidth(width);
            if (height != null)
                widget.setHeight(height);

            widget.setUpdatedDateTimeUtc(updateTime);
            return widget;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public WidgetModel get(long id) throws NotFoundException {
        lock.readLock().lock();
        try {
            ThrowIfNotExists(id);

            return widgets.get(id);
        } finally {
            lock.readLock().unlock();
        }
    }

    public WidgetModel[] getAll() throws NotFoundException {
        lock.readLock().lock();
        try {
            var ids = layerStorage.getOrderedId();
            if (ids.length == 0)
                throw new NotFoundException();

            var allWidgets = new WidgetModel[ids.length];
            for (var i = 0; i < ids.length; i++) {
                allWidgets[i] = widgets.get(ids[i]);
            }

            return allWidgets;
        } finally {
            lock.readLock().unlock();
        }
    }

    public void delete(long id) {
        lock.writeLock().lock();
        try {
            if (!widgets.containsKey(id))
                return;

            var widget = widgets.get(id);
            layerStorage.delete(widget.getZIndex());
            widgets.remove(id);
        } finally {
            lock.writeLock().unlock();
        }
    }

    private void ThrowIfNotExists(long id) throws NotFoundException {
        if (!widgets.containsKey(id))
            throw new NotFoundException(id);
    }
}

