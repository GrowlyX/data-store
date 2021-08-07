package com.solexgames.datastore.commons.connection.impl;

import com.solexgames.datastore.commons.connection.Connection;
import lombok.Getter;
import redis.clients.jedis.JedisPool;

import java.util.Optional;

/**
 * @author GrowlyX
 * @since 8/6/2021
 */

@Getter
public abstract class RedisConnection implements Connection<JedisPool> {

    @Override
    public JedisPool getConnection() {
        return new JedisPool(
                this.getAddress(),
                this.getPort()
        );
    }

    /**
     * Returns the password in optional form
     * just in-case the password provided is null.
     *
     * @return an {@link Optional} of a string
     */
    public Optional<String> getSafePassword() {
        return Optional.of(this.getPassword());
    }

    public abstract String getAddress();

    public abstract Integer getPort();

    public abstract String getPassword();

}
