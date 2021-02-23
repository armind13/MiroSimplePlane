package com.example.demo.storage;

public interface IWidgetsRepository {
    WidgetModel add(int x, int y, int width, int height, Integer zIndex);
    WidgetModel update(long id, Integer x, Integer y, Integer width, Integer height, Integer zIndex) throws NotFoundException;
    WidgetModel get(long id) throws NotFoundException;
    WidgetModel[] getAll() throws NotFoundException;
    void delete(long id);
}
