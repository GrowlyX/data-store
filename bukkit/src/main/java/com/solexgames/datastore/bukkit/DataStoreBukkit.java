package com.solexgames.datastore.bukkit;

import com.solexgames.datastore.commons.layer.impl.RedisStorageLayer;
import com.solexgames.datastore.commons.platform.DataStorePlatform;
import com.solexgames.datastore.commons.platform.DataStorePlatforms;
import com.solexgames.datastore.commons.platform.controller.StorageLayerController;
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

    private StorageLayerController storageLayerController;

    @Override
    public void enable() {
        instance = this;

        DataStorePlatforms.setCurrent(this);
    }

    @Override
    public void disable() {

    }
}
