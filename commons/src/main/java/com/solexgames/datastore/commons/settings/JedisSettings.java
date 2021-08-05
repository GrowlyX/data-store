package com.solexgames.datastore.commons.settings;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author GrowlyX
 * @since 3/1/2021
 * <p>
 * Holds jedis connection settings
 */

@Getter
@RequiredArgsConstructor
public class JedisSettings {

    private final String hostAddress;

    private final int port;
    private final boolean auth;

    private final String password;

}
