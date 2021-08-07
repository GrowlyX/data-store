package com.solexgames.datastore.commons.connection.impl;

import com.solexgames.datastore.commons.connection.Connection;
import lombok.Getter;
import redis.clients.jedis.JedisPool;

import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * @author GrowlyX
 * @since 8/6/2021
 */

@Getter
public abstract class RedisConnection implements Connection<JedisPool> {

    public Optional<String> getSafePassword() {
        return Optional.of(this.getPassword());
    }

    @Override
    public JedisPool getConnection() {
        return new JedisPool(
                this.getAddress(),
                this.getPort()
        );
    }

    public abstract String getAddress();

    public abstract Integer getPort();

    public abstract String getPassword();

}
