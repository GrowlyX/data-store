package com.solexgames.datastore.commons.platform;

import com.google.gson.Gson;
import com.solexgames.datastore.commons.platform.controller.StorageLayerController;

import java.util.logging.Logger;

/**
 * @author GrowlyX
 * @since 8/4/2021
 */

public interface DataStorePlatform {

    Gson getGson();

    Logger getLogger();

    StorageLayerController getStorageLayerController();

}
