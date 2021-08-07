package com.solexgames.datastore.commons.layer.impl;

import com.solexgames.datastore.commons.layer.AbstractStorageLayer;
import com.solexgames.datastore.commons.serializable.impl.GsonSerializable;
import com.solexgames.datastore.commons.connection.impl.RedisConnection;
import com.solexgames.datastore.commons.connection.impl.redis.AuthRedisConnection;
import lombok.Getter;
import redis.clients.jedis.Jedis;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * @author GrowlyX
 * @since 8/4/2021
 * <p>
 * An easy-to-use object based wrapper
 * for {@link Jedis}'s key-value storage system.
 */

@Getter
public class RedisStorageLayer<T>  extends AbstractStorageLayer<String, T>  {

    private final GsonSerializable<T> serializable;
    private final RedisConnection redisConnection;

    private final String section;
    private final Class<T> type;

    /**
     * Creates a new cached storage layer in redis
     *
     * @param redisConnection {@link RedisConnection} holding the jedis connection
     * @param section Channel/field to use
     * @param tClass Class to use for {@link GsonSerializable}
     *
     * @see GsonSerializable
     */
    public RedisStorageLayer(
            final RedisConnection redisConnection,
            final String section, final Class<T> tClass
    ) {
        this.section = section;
        this.type = tClass;
        this.redisConnection = redisConnection;

        this.serializable = new GsonSerializable<>(tClass);
    }

    @Override
    public CompletableFuture<Void> saveEntry(String s, T t) {
        return CompletableFuture.runAsync(() -> {
            this.runCommand(jedis -> {
                jedis.hset(this.section, s, this.serializable.serialize(t));
            });
        });
    }

    @Override
    public CompletableFuture<Void> deleteEntry(String s) {
        return CompletableFuture.runAsync(() -> {
            this.runCommand(jedis -> {
                jedis.hdel(this.section, s);
            });
        });
    }

    @Override
    public CompletableFuture<T> fetchEntryByKey(String s) {
        return CompletableFuture.supplyAsync(() -> {
            final AtomicReference<T> reference = new AtomicReference<>();

            this.runCommand(jedis -> {
                final String serializedValue = jedis.hget(this.section, s);

                if (serializedValue != null) {
                    reference.set(this.serializable.deserialize(serializedValue));
                }
            });

            return reference.get();
        });
    }

    @Override
    public CompletableFuture<Map<String, T>> fetchAllEntries() {
        return CompletableFuture.supplyAsync(() -> {
            final AtomicReference<Map<String, T>> reference = new AtomicReference<>();

            this.runCommand(jedis -> {
                final Map<String, String> serializedValue = jedis.hgetAll(this.section);

                if (serializedValue != null) {
                    final Map<String, T> newMap = new LinkedHashMap<>();

                    for (final Map.Entry<String, String> entry : serializedValue.entrySet()) {
                        newMap.put(entry.getKey(), this.serializable.deserialize(entry.getValue()));
                    }

                    reference.set(newMap);
                }
            });

            return reference.get();
        });
    }

    @Override
    public Class<T> getType() {
        return this.type;
    }

    public void runCommand(Consumer<Jedis> consumer) {
        final RedisConnection connection = this.redisConnection;

        try (final Jedis jedis = connection.getConnection().getResource()) {
            if (connection instanceof AuthRedisConnection) {
                connection.getSafePassword().ifPresent(jedis::auth);
            }

            consumer.accept(jedis);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
