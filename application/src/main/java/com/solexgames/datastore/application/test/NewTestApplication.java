package com.solexgames.datastore.application.test;

import com.solexgames.datastore.application.test.model.TestObject;
import com.solexgames.datastore.commons.connection.impl.RedisConnection;
import com.solexgames.datastore.commons.connection.impl.redis.NoAuthRedisConnection;
import com.solexgames.datastore.commons.layer.impl.RedisStorageLayer;
import com.solexgames.datastore.commons.logger.ConsoleLogger;
import com.solexgames.datastore.commons.logger.impl.SimpleConsoleLogger;
import com.solexgames.datastore.commons.platform.DataStorePlatform;
import com.solexgames.datastore.commons.storage.impl.RedisStorageBuilder;

/**
 * @author GrowlyX
 * @since 8/7/2021
 */

public class NewTestApplication extends DataStorePlatform {

    public static void main(String[] args) {
        new NewTestApplication().initalizeLayer();
    }

    public void initalizeLayer() {
        final RedisConnection noAuthRedisConnection = new NoAuthRedisConnection("127.0.0.1", 6379);
        final RedisStorageBuilder<TestObject> testClassRedisStorageBuilder = new RedisStorageBuilder<>();
        final RedisStorageLayer<TestObject> testObjectRedisStorageLayer = testClassRedisStorageBuilder
                .setSection("datastore_test")
                .setType(TestObject.class)
                .setConnection(noAuthRedisConnection)
                .build();

        this.getStorageLayerController().registerLayer("hello", testObjectRedisStorageLayer);

        // how to fetch the layer
        this.getStorageLayerController().getLayer("hello", RedisStorageLayer.class);
    }

    /**
     * the logger type to use for this app
     */
    @Override
    public ConsoleLogger getLogger() {
        return new SimpleConsoleLogger();
    }
}
