package gg.scala.store.commons.storage.impl;

import gg.scala.store.commons.connection.impl.MongoConnection;
import gg.scala.store.commons.guava.Preconditions;
import gg.scala.store.commons.layer.impl.MongoStorageLayer;
import gg.scala.store.commons.storage.StorageBuilder;
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
