package gg.scala.store.commons.connection.impl.redis;

import gg.scala.store.commons.connection.impl.RedisConnection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author GrowlyX
 * @since 8/6/2021
 * <p>
 * Forms a jedis pool without authentication.
 */

@Getter
@RequiredArgsConstructor
public class NoAuthRedisConnection extends RedisConnection {

    private final String address;
    private final Integer port;

    private final String password = null;

}
