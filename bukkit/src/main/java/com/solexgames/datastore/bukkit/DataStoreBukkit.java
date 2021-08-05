package com.solexgames.datastore.bukkit;

import org.bukkit.plugin.java.JavaPlugin;
import lombok.Getter;

/**
 * @author GrowlyX
 * @since 8/4/2021
 */

@Getter
public final class DataStoreBukkit extends JavaPlugin {

    @Getter
    private static DataStoreBukkit instance;

    @Override
    public void onEnable() {
        instance = this;
    }

    @Override
    public void onDisable() {

    }
}
