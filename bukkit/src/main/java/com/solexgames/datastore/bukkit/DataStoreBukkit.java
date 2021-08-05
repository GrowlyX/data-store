package com.solexgames.datastore.bukkit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.solexgames.datastore.commons.layer.impl.RedisStorageLayer;
import com.solexgames.datastore.commons.platform.DataStorePlatform;
import com.solexgames.datastore.commons.platform.DataStorePlatforms;
import com.solexgames.datastore.commons.platform.controller.StorageLayerController;
import com.solexgames.datastore.commons.settings.JedisSettings;
import com.solexgames.datastore.commons.storage.impl.RedisStorageBuilder;
import lombok.Getter;
import me.lucko.helper.plugin.ExtendedJavaPlugin;

/**
 * @author GrowlyX
 * @since 8/4/2021
 */

@Getter
public final class DataStoreBukkit extends ExtendedJavaPlugin implements DataStorePlatform {

    @Getter
    private static DataStoreBukkit instance;

    private final Gson gson = new GsonBuilder()
            .serializeNulls()
            .create();

    private StorageLayerController storageLayerController;

    @Override
    public void enable() {
        instance = this;

        DataStorePlatforms.setCurrent(this);

//        this.storageLayerController = new BukkitStorageLayerController();

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
                )).build();

        storageLayer.saveEntry("test", this).whenComplete((unused, throwable) -> {
            if (throwable != null) {
                throwable.printStackTrace();
            }

            System.out.println("the save entry method was ran!");
        });


        storageLayer.deleteEntry("test").whenComplete((unused, throwable) -> {
            if (throwable != null) {
                throwable.printStackTrace();
            }

            System.out.println("the delete entry method was ran!");
        });
    }

    @Override
    public void disable() {

    }
}
