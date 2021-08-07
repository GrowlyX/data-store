package com.solexgames.datastore.commons.logger.impl;

import com.solexgames.datastore.commons.constants.ConsoleColor;
import com.solexgames.datastore.commons.logger.ConsoleLogger;

/**
 * @author GrowlyX
 * @since 8/6/2021
 */

public class SimpleConsoleLogger extends ConsoleLogger {

    @Override
    public void log(String message) {
        System.out.println(ConsoleColor.CYAN + "[Data Store] " + ConsoleColor.RESET + message + ConsoleColor.RESET);
    }
}
