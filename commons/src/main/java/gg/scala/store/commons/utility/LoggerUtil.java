package gg.scala.store.commons.utility;

import gg.scala.store.commons.platform.DataStorePlatform;

/**
 * @author GrowlyX
 * @since 8/7/2021
 */

public class LoggerUtil {

    public static void log(String message) {
        DataStorePlatform.current().getLogger().log(message);
    }
}
