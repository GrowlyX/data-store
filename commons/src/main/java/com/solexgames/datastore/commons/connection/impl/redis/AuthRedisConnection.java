package com.solexgames.datastore.commons.connection.impl.redis;

import com.solexgames.datastore.commons.connection.impl.RedisConnection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author GrowlyX
 * @since 8/6/2021
 * <p>
 * Forms a jedis pool with authentication.
 */

@Getter
@RequiredArgsConstructor
public class AuthRedisConnection extends RedisConnection {

    private final String address;
    private final Integer port;

    private final String password;

}
