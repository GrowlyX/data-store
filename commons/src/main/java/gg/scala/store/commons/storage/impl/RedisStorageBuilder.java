package gg.scala.store.commons.storage.impl;

import gg.scala.store.commons.guava.Preconditions;
import gg.scala.store.commons.layer.impl.RedisStorageLayer;
import gg.scala.store.commons.connection.impl.RedisConnection;
import gg.scala.store.commons.storage.StorageBuilder;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author GrowlyX
 * @since 8/4/2021
 */

@Setter
@Accessors(chain = true)
public class RedisStorageBuilder<T> implements StorageBuilder<RedisStorageLayer<T>> {

    private RedisConnection connection;

    private Class<T> type;
    private String section;

    /**
     * Forms a new redis layer with
     * the provided connection & type.
     *
     * @return a {@link RedisStorageLayer} with the data
     */
    @Override
    public RedisStorageLayer<T> build() {
        Preconditions.checkNotNull(this.connection);
        Preconditions.checkNotNull(this.type);
        Preconditions.checkNotNull(this.section);

        return new RedisStorageLayer<>(
                this.connection,
                this.section,
                this.type
        );
    }
}
