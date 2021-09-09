package com.solexgames.datastore.commons.storage.impl;

import com.solexgames.datastore.commons.connection.impl.MongoConnection;
import com.solexgames.datastore.commons.connection.impl.RedisConnection;
import com.solexgames.datastore.commons.guava.Preconditions;
import com.solexgames.datastore.commons.layer.impl.MongoStorageLayer;
import com.solexgames.datastore.commons.layer.impl.RedisStorageLayer;
import com.solexgames.datastore.commons.storage.StorageBuilder;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author GrowlyX
 * @since 8/4/2021
 */

@Setter
@Accessors(chain = true)
public class MongoStorageBuilder<T> implements StorageBuilder<MongoStorageLayer<T>> {

    private MongoConnection connection;

    private Class<T> type;

    private String database;
    private String collection;

    @Override
    public MongoStorageLayer<T> build() {
        Preconditions.checkNotNull(this.connection);
        Preconditions.checkNotNull(this.type);
        Preconditions.checkNotNull(this.database);
        Preconditions.checkNotNull(this.collection);

        return new MongoStorageLayer<>(
                this.connection,
                this.database,
                this.collection,
                this.type
        );
    }
}
