package com.example.demo.unit;

import com.example.demo.storage.*;
import com.example.demo.storage.inmemory.InMemoryWidgetsRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InMemoryWidgetsRepositoryTests {

    private final EasyRandom random = new EasyRandom();

    @Test
    void add_WhenZIndexSet_ShouldReturnWidgetWithZIndex() {
        var repo = new InMemoryWidgetsRepository(new DateTimeProvider());

        var x = random.nextInt();
        var y = random.nextInt();
        var width = random.nextInt();
        var height = random.nextInt();
        var zIndex = random.nextInt();

        var widget = repo.add(x, y, zIndex, width, height);

        assertThat(widget.getX()).isEqualTo(x);
        assertThat(widget.getY()).isEqualTo(y);
        assertThat(widget.getWidth()).isEqualTo(width);
        assertThat(widget.getHeight()).isEqualTo(height);
        assertThat(widget.getZIndex()).isEqualTo(zIndex);
    }

    @Test
    void add_WhenZIndexDoesNotSet_ShouldReturnWidgetWithZIndex() {
        var repo = new InMemoryWidgetsRepository(new DateTimeProvider());
        var zIndex = random.nextInt();

        repo.add(random.nextInt(), random.nextInt(), zIndex, random.nextInt(), random.nextInt());
        var w2 = repo.add(random.nextInt(), random.nextInt(), null, random.nextInt(), random.nextInt());

        assertThat(w2.getZIndex()).isEqualTo(zIndex + 1);
    }

    @Test
    void update_WhenIdDoesNotExist_ShouldThrow() {
        var repo = new InMemoryWidgetsRepository(new DateTimeProvider());

        var id = random.nextLong();
        var exception = assertThrows(
                NotFoundException.class,
                () -> repo.update(id, null, null, null, null, null));

        assertThat(exception.getId()).isEqualTo(id);
    }

    @Test
    void update_WhenIdExists_ShouldUpdateSpecifiedFields() throws NotFoundException {

        var firstCall = OffsetDateTime.now(ZoneOffset.UTC);
        var secondCall = firstCall.plusMinutes(1);
        var dateTimeProviderMock = Mockito.mock(IDateTimeProvider.class);
        Mockito.when(dateTimeProviderMock.GetNow()).thenReturn(firstCall, secondCall);

        var repo = new InMemoryWidgetsRepository(dateTimeProviderMock);
        var widget = repo.add(random.nextInt(), random.nextInt(), random.nextInt(), random.nextInt(), random.nextInt());

        int x = random.nextInt();
        int y = random.nextInt();
        int width = random.nextInt();
        int height = random.nextInt();
        int zIndex = random.nextInt();
        repo.update(widget.getId(), x, y, zIndex, width, height);

        assertThat(widget.getX()).isEqualTo(x);
        assertThat(widget.getY()).isEqualTo(y);
        assertThat(widget.getWidth()).isEqualTo(width);
        assertThat(widget.getHeight()).isEqualTo(height);
        assertThat(widget.getZIndex()).isEqualTo(zIndex);
        assertThat(widget.getUpdatedDateTimeUtc().compareTo(secondCall)).isEqualTo(0);
    }

    @Test
    void get_WhenIdDoesNotExist_ShouldThrow() {
        var repo = new InMemoryWidgetsRepository(new DateTimeProvider());

        var id = random.nextLong();
        var exception = assertThrows(
                NotFoundException.class,
                () -> repo.get(id));

        assertThat(exception.getId()).isEqualTo(id);
    }

    @Test
    void get_WhenIdExists_ShouldReturnWidget() {
        var repo = new InMemoryWidgetsRepository(new DateTimeProvider());

        int x = random.nextInt();
        int y = random.nextInt();
        int width = random.nextInt();
        int height = random.nextInt();
        int zIndex = random.nextInt();
        var widget = repo.add(x, y, zIndex, width, height);

        assertThat(widget.getX()).isEqualTo(x);
        assertThat(widget.getY()).isEqualTo(y);
        assertThat(widget.getWidth()).isEqualTo(width);
        assertThat(widget.getHeight()).isEqualTo(height);
        assertThat(widget.getZIndex()).isEqualTo(zIndex);
    }

    @Test
    void getAll_WhenRepoIsEmpty_ShouldThrow() {
        var repo = new InMemoryWidgetsRepository(new DateTimeProvider());

        var exception = assertThrows(
                NotFoundException.class,
                () -> repo.getAll()
        );

        assertThat(exception.toString()).isEqualTo("Repository is empty");
    }

    @Test
    void getAll_WhenWidgetsExist_ShouldReturnOrderedWidgets() throws NotFoundException {
        var repo = new InMemoryWidgetsRepository(new DateTimeProvider());
        for (var i = 0; i < 100; i++) {
            repo.add(random.nextInt(), random.nextInt(), random.nextInt(), random.nextInt(), random.nextInt());
        }

        var widgets = repo.getAll();

        assertThat(widgets).isSortedAccordingTo(Comparator.comparingInt(WidgetModel::getZIndex));
    }

    @Test
    void delete_WhenWidgetExists_ShouldDelete() throws NotFoundException {
        var repo = new InMemoryWidgetsRepository(new DateTimeProvider());

        var w1 = repo.add(random.nextInt(), random.nextInt(), random.nextInt(), random.nextInt(), random.nextInt());
        repo.add(random.nextInt(), random.nextInt(), random.nextInt(), random.nextInt(), random.nextInt());
        repo.delete(w1.getId());

        var widgets = repo.getAll();

        assertThat(widgets).doesNotContain(w1);
    }
}
