package com.example.demo;

import com.example.demo.storage.LayerStorage;
import com.example.demo.storage.WidgetModel;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
class LayerStorageTests {

    @Test
    void add_WhenWidgetsDoesNotInsertYet_ShouldInset() {
        int id = 1;
        var zIndex = 5;
        var storage = new LayerStorage();

        var w1 = addWidget(storage, id, zIndex);

        var orderedIds = storage.getOrderedId();
        var maxZIndex = storage.getMaxZIndex();
        assertThat(orderedIds).hasSize(1);
        assertThat(maxZIndex).isEqualTo(zIndex);
    }

    @Test
    void add_WhenNewWidgetHasGreaterZIndex_ShouldAddAfter() {
        var id1 = 1;
        var id2 = 2;
        var storage = new LayerStorage();
        int zIndex1 = 1;
        int zIndex2 = 3;

        addWidget(storage, id1, zIndex1);
        addWidget(storage, id2, zIndex2);

        var orderedIds = storage.getOrderedId();
        assertThat(orderedIds).isEqualTo(new long[] {id1, id2});
    }

    @Test
    void add_WhenNewWidgetHasLessZIndex_ShouldAddAfter() {
        var id1 = 1;
        var id2 = 2;
        var storage = new LayerStorage();
        int zIndex1 = 3;
        int zIndex2 = 1;

        addWidget(storage, id1, zIndex1);
        addWidget(storage, id2, zIndex2);

        var orderedIds = storage.getOrderedId();
        assertThat(orderedIds).isEqualTo(new long[] {id2, id1});
    }

    @Test
    void add_WhenNewWidgetHasEqualZIndex_ShouldAddAfter() {
        var id1 = 1;
        var id2 = 2;
        var storage = new LayerStorage();
        int zIndex = 3;

        var w1 = addWidget(storage, id1, zIndex);
        var w2 = addWidget(storage, id2, zIndex);

        var orderedIds = storage.getOrderedId();
        assertThat(orderedIds).isEqualTo(new long[] {id2, id1});
        assertThat(w2.getZIndex()).isEqualTo(zIndex);
        assertThat(w1.getZIndex()).isEqualTo(zIndex + 1);
    }

    @Test
    void add_WhenNewWidgetHasEqualZIndex_And_NeedFewShifts_ShouldShift() {
        var id1 = 1;
        var id2 = 2;
        var id3 = 3;
        var id4 = 4;
        var storage = new LayerStorage();

        var w1 = addWidget(storage, id1, 5);
        var w2 = addWidget(storage, id2, 2);
        var w3 = addWidget(storage, id3, 3);
        var w4 = addWidget(storage, id4, 2);

        var orderedIds = storage.getOrderedId();
        assertThat(orderedIds).isEqualTo(new long[] {id4, id2, id3, id1});
        assertThat(w4.getZIndex()).isEqualTo(2);
        assertThat(w2.getZIndex()).isEqualTo(3);
        assertThat(w3.getZIndex()).isEqualTo(4);
        assertThat(w1.getZIndex()).isEqualTo(5);
    }

    @Test
    void delete_WhenIndexExists_ShouldDelete() {
        var id1 = 1;
        var id2 = 2;
        int zIndex = 3;
        var storage = new LayerStorage();
        addWidget(storage, id1, 5);
        addWidget(storage, id2, zIndex);

        storage.delete(zIndex);

        var orderedIds = storage.getOrderedId();
        assertThat(orderedIds).isEqualTo(new long[] {id1});
    }

    @Test
    void delete_WhenIndexDoesNotExist_ShouldSkip() {
        var id1 = 1;
        var id2 = 2;
        var storage = new LayerStorage();
        addWidget(storage, id1, 5);
        addWidget(storage, id2, 3);

        storage.delete(7);

        var orderedIds = storage.getOrderedId();
        assertThat(orderedIds).isEqualTo(new long[] {id2, id1});
    }

    @Test
    void update_WhenIndexExist_ShouldShiftGreaterIndexes() {
        var id1 = 1;
        var id2 = 2;
        var id3 = 3;
        var id4 = 4;
        var storage = new LayerStorage();
        var w1 = addWidget(storage, id1, 5);
        var w2 = addWidget(storage, id2, 3);
        var w3 = addWidget(storage, id3, 4);
        var w4 = addWidget(storage, id4, 2);

        storage.update(2, 4);

        var orderedIds = storage.getOrderedId();
        assertThat(orderedIds).isEqualTo(new long[] {id2, id4, id3, id1});
        assertThat(w2.getZIndex()).isEqualTo(3);
        assertThat(w4.getZIndex()).isEqualTo(4);
        assertThat(w3.getZIndex()).isEqualTo(5);
        assertThat(w1.getZIndex()).isEqualTo(6);
    }

    private WidgetModel addWidget(LayerStorage storage, long id, int zIndex) {
        var widget =  new WidgetModel(id, 1, 1, 1, 1, OffsetDateTime.now());
        storage.add(widget, zIndex);

        return widget;
    }
}

