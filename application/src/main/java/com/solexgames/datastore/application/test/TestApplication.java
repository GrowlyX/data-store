package com.solexgames.datastore.application.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.solexgames.datastore.commons.layer.AbstractStorageLayer;
import com.solexgames.datastore.commons.layer.impl.RedisStorageLayer;
import com.solexgames.datastore.commons.platform.DataStorePlatform;
import com.solexgames.datastore.commons.platform.DataStorePlatforms;
import com.solexgames.datastore.commons.platform.controller.StorageLayerController;
import com.solexgames.datastore.commons.connection.impl.RedisConnection;
import com.solexgames.datastore.commons.connection.impl.redis.NoAuthRedisConnection;
import com.solexgames.datastore.commons.storage.impl.RedisStorageBuilder;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * @author GrowlyX
 * @since 8/5/2021
 */

@Getter
public class TestApplication implements DataStorePlatform {

    private final Gson gson = new GsonBuilder()
            .serializeNulls()
            .create();

    private final StorageLayerController storageLayerController = new StorageLayerController();

    {
        DataStorePlatforms.setCurrent(this);
    }

    @Setter
    private static boolean running = true;

    @SneakyThrows
    public static void main(String[] args) {
        final TestApplication application = new TestApplication();
        application.initialize();

        while (running) {
            final BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in));

            final String command = reader.readLine();

            if (command != null) {
                final String[] commandArgs = command.split(" ");
                final long startTime = System.currentTimeMillis();

                final AbstractStorageLayer<String, TestObject> storageLayerControllerLayer =
                        application.getStorageLayerController().getLayer("hello", TestObject.class);

                switch (commandArgs[0]) {
                    case "save":
                        if (commandArgs.length == 3) {
                            final TestObject testObject = new TestObject(commandArgs[2]);

                            storageLayerControllerLayer.saveEntry(commandArgs[1], testObject).whenComplete((unused, throwable) -> {
                                System.out.println("saved entry!");

                                System.out.println("Took " + (System.currentTimeMillis() - startTime) + "ms to run this jedis command.");
                            });
                        } else {
                            System.out.println("no");
                        }
                        break;
                    case "fetch":
                        if (commandArgs.length == 2) {
                            storageLayerControllerLayer.fetchEntryByKey(commandArgs[1]).whenComplete((testObject1, throwable) -> {
                                if (testObject1 != null) {
                                    System.out.println("fetched entry! result: " + testObject1.getTest());
                                } else {
                                    System.out.println("I couldn't find that entry!");
                                }

                                System.out.println("Took " + (System.currentTimeMillis() - startTime) + "ms to run this jedis command.");
                            });
                        } else {
                            System.out.println("no");
                        }
                        break;
                    case "fetchall":
                        storageLayerControllerLayer.fetchAllEntries().whenComplete((entries, throwable) -> {
                            if (entries == null || entries.isEmpty()) {
                                System.out.println("There aren't any entries!");
                            } else {
                                final AtomicInteger atomicInteger = new AtomicInteger();

                                entries.forEach((s, testObject1) -> {
                                    System.out.println(atomicInteger.incrementAndGet() + ". " + s + " - " + testObject1.getTest());
                                });
                            }

                            System.out.println("Took " + (System.currentTimeMillis() - startTime) + "ms to run this jedis command.");
                        });
                        break;
                }
            }
        }
    }

    private void initialize() {
        final RedisConnection noAuthRedisConnection = new NoAuthRedisConnection("127.0.0.1", 6379);
        final RedisStorageBuilder<TestObject> testClassRedisStorageBuilder = new RedisStorageBuilder<>();
        final RedisStorageLayer<TestObject> testObjectRedisStorageLayer = testClassRedisStorageBuilder
                .setSection("datastore_test")
                .setType(TestObject.class)
                .setConnection(noAuthRedisConnection)
                .build();

        this.storageLayerController.registerLayer("hello", testObjectRedisStorageLayer);

        final AbstractStorageLayer<String, TestObject> storageLayerControllerLayer =
                this.storageLayerController.getLayer("hello", TestObject.class);
        final TestObject testObject = new TestObject("test");

        storageLayerControllerLayer.saveEntry("testing", testObject).whenComplete((unused, throwable) -> {
           this.getLogger().info("The layer fetched from the storage layer worked.");
        });

        this.getLogger().info("Initialized redis storage layer, now waiting for commands...");
    }

    @Override
    public Logger getLogger() {
        return Logger.getGlobal();
    }
}
