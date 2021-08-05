package com.solexgames.datastore.application.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.solexgames.datastore.commons.layer.impl.RedisStorageLayer;
import com.solexgames.datastore.commons.platform.DataStorePlatform;
import com.solexgames.datastore.commons.platform.DataStorePlatforms;
import com.solexgames.datastore.commons.platform.controller.StorageLayerController;
import com.solexgames.datastore.commons.settings.JedisSettings;
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

    private StorageLayerController storageLayerController;

    private RedisStorageLayer<TestObject> layer;

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

                switch (commandArgs[0]) {
                    case "save":
                        if (commandArgs.length == 3) {
                            final TestObject testObject = new TestObject(commandArgs[2]);

                            application.getLayer().saveEntry(commandArgs[1], testObject).whenComplete((unused, throwable) -> {
                                System.out.println("saved entry!");
                            });
                        } else {
                            System.out.println("no");
                        }
                        break;
                    case "fetch":
                        if (commandArgs.length == 2) {
                            application.getLayer().fetchEntryByKey(commandArgs[1]).whenComplete((testObject1, throwable) -> {
                                if (testObject1 != null) {
                                    System.out.println("fetched entry! " + testObject1);
                                } else {
                                    System.out.println("I couldn't find that entry!");
                                }
                            });
                        } else {
                            System.out.println("no");
                        }
                        break;
                    case "fetchall":
                        application.getLayer().fetchAllEntries().whenComplete((entries, throwable) -> {
                            if (entries == null || entries.isEmpty()) {
                                System.out.println("There aren't any entries!");
                            } else {
                                final AtomicInteger atomicInteger = new AtomicInteger();

                                entries.forEach((s, testObject1) -> {
                                    System.out.println(atomicInteger.incrementAndGet() + ". " + s + " - " + testObject1.getTest());
                                });
                            }
                        });
                        break;
                }
            }
        }
    }

    private void initialize() {
        final RedisStorageBuilder<TestObject> testClassRedisStorageBuilder = new RedisStorageBuilder<>();

        this.layer = testClassRedisStorageBuilder
                .setSection("datastore_test")
                .setTClass(TestObject.class)
                .setJedisSettings(new JedisSettings(
                        "panel.clox.us",
                        6379,
                        false,
                        ""
                )).build();

        this.getLogger().info("Initialized redis storage layer, now waiting for commands...");
    }

    @Override
    public Logger getLogger() {
        return Logger.getGlobal();
    }
}
