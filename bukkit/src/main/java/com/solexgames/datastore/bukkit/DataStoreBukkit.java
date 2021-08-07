package com.solexgames.datastore.bukkit;

import lombok.Getter;
import me.lucko.helper.plugin.ExtendedJavaPlugin;

/**
 * @author GrowlyX
 * @since 8/4/2021
 */

@Getter
public final class DataStoreBukkit extends ExtendedJavaPlugin {

    @Getter
    private static DataStoreBukkit instance;

    @Override
    public void enable() {
        instance = this;
    }

    @Override
    public void disable() {

    }
}
