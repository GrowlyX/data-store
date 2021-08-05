package com.solexgames.datastore.commons.storage;

/**
 * @author GrowlyX
 * @since 8/4/2021
 * <p>
 * Creates a factory-based creation
 * system for storage layers.
 */

public interface StorageBuilder<T> {

    T build();

}
