package com.solexgames.datastore.bukkit;

import com.solexgames.datastore.commons.layer.impl.RedisStorageLayer;
import com.solexgames.datastore.commons.settings.JedisSettings;
import com.solexgames.datastore.commons.storage.impl.RedisStorageBuilder;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

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

        this.test();
    }

    private void test() {
        final RedisStorageBuilder<DataStoreBukkit> testClassRedisStorageBuilder = new RedisStorageBuilder<>();
        final RedisStorageLayer<DataStoreBukkit> storageLayer = testClassRedisStorageBuilder
                .setSection("some random section")
                .setJedisSettings(new JedisSettings(
                        "127.0.0.1",
                        6379,
                        false,
                        ""
                ))
                .build();

        storageLayer.saveEntry("test", this).whenComplete((unused, throwable) -> {
            if (throwable != null) {
                throwable.printStackTrace();
            }

            System.out.println("the save entry method was ran!");
        });
    }

    @Override
    public void onDisable() {

    }
}
