package com.solexgames.datastore.commons.platform.controller;

import com.solexgames.datastore.commons.layer.AbstractStorageLayer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author GrowlyX
 * @since 8/5/2021
 */

public class StorageLayerController {

    private final Map<String, Map<Class<?>, AbstractStorageLayer<?, ?>>> layerStorage = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <K, V> AbstractStorageLayer<K, V> getLayer(String id, Class<V> vClass) {
        return (AbstractStorageLayer<K, V>) this.layerStorage
                .getOrDefault(id, new HashMap<>())
                .getOrDefault(vClass, null);
    }

    public void registerLayer(String id, AbstractStorageLayer<?, ?> layer) {
        final Map<Class<?>, AbstractStorageLayer<?, ?>> map = this.layerStorage
                .computeIfAbsent(id, k -> new HashMap<>());

        map.put(layer.getType(), layer);
    }
}
