package com.solexgames.datastore.commons.platform;

/**
 * @author GrowlyX
 * @since 8/4/2021
 */

public class DataStorePlatforms {

    private static DataStorePlatform CURRENT;

    public static DataStorePlatform current() {
        return CURRENT;
    }

    public static void setCurrent(DataStorePlatform platform) {
        DataStorePlatforms.CURRENT = platform;
    }
}
