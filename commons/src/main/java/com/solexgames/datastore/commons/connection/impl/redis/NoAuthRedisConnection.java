package com.solexgames.datastore.commons.connection.impl.redis;

import com.solexgames.datastore.commons.connection.impl.RedisConnection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author GrowlyX
 * @since 8/6/2021
 */

@Getter
@RequiredArgsConstructor
public class NoAuthRedisConnection extends RedisConnection {

    private final String address;
    private final Integer port;

    @Override
    public String getPassword() {
        return null;
    }
}
