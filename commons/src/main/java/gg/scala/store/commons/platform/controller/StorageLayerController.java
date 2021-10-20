package gg.scala.store.commons.platform.controller;

import gg.scala.store.commons.layer.AbstractStorageLayer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author GrowlyX
 * @since 8/5/2021
 */

public class StorageLayerController {

    private final Map<String, Map<Class<?>, AbstractStorageLayer<?, ?>>> layerStorage = new HashMap<>();

    /**
     * Get an {@link AbstractStorageLayer} by its id & class type
     *
     * @param id the id to fetch the layer by
     * @param vClass the value class, which is also the object that will be serialized to a string
     * @param <K> the key type
     * @param <V> the value type
     *
     * @return a {@link AbstractStorageLayer} with types {@link K} & {@link V}
     */
    @SuppressWarnings("unchecked")
    public <K, V> AbstractStorageLayer<K, V> getLayer(String id, Class<V> vClass) {
        return (AbstractStorageLayer<K, V>) this.layerStorage
                .getOrDefault(id, new HashMap<>())
                .getOrDefault(vClass, null);
    }

    /**
     * Registers a layer in the layer map
     *
     * @param id the id to identify the layer
     * @param layer the {@link AbstractStorageLayer}
     */
    public void registerLayer(String id, AbstractStorageLayer<?, ?> layer) {
        final Map<Class<?>, AbstractStorageLayer<?, ?>> map = this.layerStorage
                .computeIfAbsent(id, k -> new HashMap<>());

        map.put(layer.getType(), layer);
    }
}
