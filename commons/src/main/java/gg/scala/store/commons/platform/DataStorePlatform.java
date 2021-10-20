package gg.scala.store.commons.platform;

import gg.scala.store.commons.platform.controller.StorageLayerController;

/**
 * @author GrowlyX
 * @since 8/4/2021
 */

public abstract class DataStorePlatform {

    private static DataStorePlatform CURRENT;

    private final StorageLayerController storageLayerController = new StorageLayerController();

    public DataStorePlatform() {
        DataStorePlatform.setCurrent(this);
    }

    public StorageLayerController getStorageLayerController() {
        return this.storageLayerController;
    }

    public static DataStorePlatform current() {
        return CURRENT;
    }

    public static void setCurrent(DataStorePlatform platform) {
        DataStorePlatform.CURRENT = platform;
    }

}
