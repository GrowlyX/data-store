package com.solexgames.datastore.commons.layer.impl;

import com.solexgames.datastore.commons.layer.AbstractStorageLayer;
import com.solexgames.datastore.commons.serializable.impl.GsonSerializable;
import com.solexgames.datastore.commons.settings.JedisSettings;
import lombok.Getter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

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

    private final JedisPool jedisPool;
    private final JedisSettings jedisSettings;

    private final String section;

    /**
     * Creates a new cached storage layer in redis
     *
     * @param jedisPool Jedis pool to get the resource from
     * @param jedisSettings Authentication settings
     * @param section Channel/field to use
     * @param tClass Class to use for {@link GsonSerializable}
     *
     * @see GsonSerializable
     */
    public RedisStorageLayer(
            JedisPool jedisPool, JedisSettings jedisSettings,
            String section, Class<T> tClass
    ) {
        this.jedisPool = jedisPool;
        this.jedisSettings = jedisSettings;
        this.section = section;

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

    public void runCommand(Consumer<Jedis> consumer) {
        try (final Jedis jedis = this.jedisPool.getResource()) {
            if (this.jedisSettings.isAuth()) {
                jedis.auth(this.jedisSettings.getPassword());
            }

            consumer.accept(jedis);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
