package com.example.demo.storage;

import java.util.HashMap;
import java.util.TreeSet;

public class LayerStorage {

    private TreeSet<Integer> layersSet = new TreeSet<>();

    private HashMap<Integer, ILayerUpdater> relations = new HashMap<>();

    public void Add(ILayerUpdater updater, int zIndex) {
        var toInsert = updater;
        var currentIndex = zIndex;

        while (relations.containsKey(currentIndex)) {
            var temp = relations.get(currentIndex);

            relations.put(zIndex, toInsert);
            toInsert.setZIndex(zIndex);
            currentIndex++;
            toInsert = temp;
        }

        layersSet.add(zIndex);
        relations.put(zIndex, updater);
        updater.setZIndex(zIndex);
    }

    public void Delete(int zIndex) {
        if (relations.containsKey(zIndex)) {
            relations.remove(zIndex);
            layersSet.remove(zIndex);
        }
    }

    public void Update(int from, int to) {
        if (!relations.containsKey(from))
            return;

        var updater = relations.get(from);
        Delete(from);
        Add(updater, to);
    }

    public long[] GetOrderedId() {
        var ids = new long[layersSet.size()];

        var i = 0;
        for (var zIndex : layersSet) {
            ids[i++] = relations.get(zIndex).getId();
        }

        return ids;
    }

    public int GetMaxZIndex() {
        if (layersSet.size() > 0)
            return layersSet.last();
        return 0;
    }
}

