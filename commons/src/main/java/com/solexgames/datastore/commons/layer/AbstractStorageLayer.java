package com.solexgames.datastore.commons.layer;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author GrowlyX
 * @since 8/4/2021
 */

public abstract class AbstractStorageLayer<K, V> {

    public abstract CompletableFuture<Void> saveEntry(K k, V v);

    public abstract CompletableFuture<Void> deleteEntry(K k);

    public abstract CompletableFuture<V> fetchEntryByKey(K k);

    public abstract CompletableFuture<Map<K, V>> fetchAllEntries();

    public abstract Type getType();

}
