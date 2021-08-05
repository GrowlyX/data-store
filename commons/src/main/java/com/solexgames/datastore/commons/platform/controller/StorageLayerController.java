package com.solexgames.datastore.commons.platform.controller;

import com.solexgames.datastore.commons.layer.AbstractStorageLayer;

import java.util.Map;

/**
 * @author GrowlyX
 * @since 8/4/2021
 */

public interface StorageLayerController {

    Map<Class<?>, Object> getStorageLayers();

    <T> T getStorageLayer(Class<? extends T> clazz);

    void registerStorageLayer(AbstractStorageLayer<?, ?> abstractStorageLayer);

}
