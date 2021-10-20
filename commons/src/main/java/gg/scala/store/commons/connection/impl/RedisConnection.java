package gg.scala.store.commons.connection.impl;

import gg.scala.store.commons.connection.Connection;
import lombok.Getter;
import redis.clients.jedis.JedisPool;

import java.util.Optional;

/**
 * @author GrowlyX
 * @since 8/6/2021
 */

@Getter
public abstract class RedisConnection implements Connection<JedisPool> {

    private final JedisPool connection;

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

    {
        connection = new JedisPool(
                this.getAddress() == null ? "127.0.0.1" : this.getAddress(),
                this.getPort() == null ? 6379 : this.getPort()
        );
    }

}