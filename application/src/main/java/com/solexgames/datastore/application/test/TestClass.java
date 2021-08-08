package com.solexgames.datastore.application.test;

import com.solexgames.datastore.application.test.model.TestObject;
import com.solexgames.datastore.commons.layer.AbstractStorageLayer;
import com.solexgames.datastore.commons.platform.DataStorePlatform;
import com.solexgames.datastore.commons.utility.LoggerUtil;
import lombok.RequiredArgsConstructor;

/**
 * @author GrowlyX
 * @since 8/7/2021
 */

@RequiredArgsConstructor
public class TestClass {

    private final DataStorePlatform platform;

    public void doSomething() {
        final AbstractStorageLayer<String, TestObject> storageLayerControllerLayer =
                this.platform.getStorageLayerController().getLayer("hello", TestObject.class);

        storageLayerControllerLayer.saveEntry("something", new TestObject("did something")).whenComplete((unused, throwable) -> {
            LoggerUtil.log("did something lol");
        });
    }
}
