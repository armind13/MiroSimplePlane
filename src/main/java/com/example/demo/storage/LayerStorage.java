package com.example.demo.storage;

import java.util.HashMap;
import java.util.TreeSet;

public class LayerStorage {

    private final TreeSet<Integer> layersSet = new TreeSet<>();

    private final HashMap<Integer, ILayerUpdater> relations = new HashMap<>();

    public void add(ILayerUpdater updater, int zIndex) {
        var toInsert = updater;
        var currentIndex = zIndex;

        while (relations.containsKey(currentIndex)) {
            var temp = relations.get(currentIndex);

            relations.put(currentIndex, toInsert);
            toInsert.setZIndex(currentIndex);
            currentIndex++;
            toInsert = temp;
        }

        layersSet.add(currentIndex);
        relations.put(currentIndex, toInsert);
        toInsert.setZIndex(currentIndex);
    }

    public void delete(int zIndex) {
        if (!relations.containsKey(zIndex))
            return;

        relations.remove(zIndex);
        layersSet.remove(zIndex);
    }

    public void update(int from, int to) {
        if (!relations.containsKey(from))
            return;

        var updater = relations.get(from);
        delete(from);
        add(updater, to);
    }

    public long[] getOrderedId() {
        var ids = new long[layersSet.size()];

        var i = 0;
        for (var zIndex : layersSet) {
            ids[i++] = relations.get(zIndex).getId();
        }

        return ids;
    }

    public int getMaxZIndex() {
        return layersSet.size() > 0
                ? layersSet.last()
                : 0;
    }
}

