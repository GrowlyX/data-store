package com.solexgames.datastore.commons.storage.impl;

import com.google.common.base.Preconditions;
import com.solexgames.datastore.commons.layer.impl.RedisStorageLayer;
import com.solexgames.datastore.commons.settings.JedisSettings;
import com.solexgames.datastore.commons.storage.StorageBuilder;
import lombok.Getter;
import redis.clients.jedis.JedisPool;

/**
 * @author GrowlyX
 * @since 8/4/2021
 */

@Getter
public class RedisStorageBuilder<T> implements StorageBuilder<RedisStorageLayer<T>> {

    private JedisSettings jedisSettings;

    private Class<T> tClass;
    private String section;

    public RedisStorageBuilder<T> setSettings(JedisSettings jedisSettings) {
        this.jedisSettings = jedisSettings;
        return this;
    }

    public RedisStorageBuilder<T> setClass(Class<T> tClass) {
        this.tClass = tClass;
        return this;
    }

    public RedisStorageBuilder<T> setSection(String section) {
        this.section = section;
        return this;
    }

    @Override
    public RedisStorageLayer<T> build() {
        Preconditions.checkNotNull(this.jedisSettings);
        Preconditions.checkNotNull(this.tClass);
        Preconditions.checkNotNull(this.section);

        return new RedisStorageLayer<>(
                new JedisPool(
                        this.jedisSettings.getHostAddress(),
                        this.jedisSettings.getPort()
                ),
                this.jedisSettings,
                this.section,
                this.tClass
        );
    }
}
