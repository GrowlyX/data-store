package com.solexgames.datastore.application.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.solexgames.datastore.application.test.model.TestObject;
import com.solexgames.datastore.commons.constants.ConsoleColor;
import com.solexgames.datastore.commons.layer.AbstractStorageLayer;
import com.solexgames.datastore.commons.layer.impl.RedisStorageLayer;
import com.solexgames.datastore.commons.logger.ConsoleLogger;
import com.solexgames.datastore.commons.logger.impl.SimpleConsoleLogger;
import com.solexgames.datastore.commons.platform.DataStorePlatform;
import com.solexgames.datastore.commons.connection.impl.RedisConnection;
import com.solexgames.datastore.commons.connection.impl.redis.NoAuthRedisConnection;
import com.solexgames.datastore.commons.storage.impl.RedisStorageBuilder;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author GrowlyX
 * @since 8/5/2021
 */

@Getter
public class TestApplication extends DataStorePlatform {

    private final Gson gson = new GsonBuilder()
            .serializeNulls()
            .create();

    private final ConsoleLogger logger = new SimpleConsoleLogger();

    @Setter
    private static boolean running = true;

    private final TestClass testClass = new TestClass(this);

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
                    case "something":let
                        application.getTestClass().doSomething();
                        break;
                    case "save":
                        if (commandArgs.length == 3) {
                            final TestObject testObject = new TestObject(commandArgs[2]);

                            storageLayerControllerLayer.saveEntry(commandArgs[1], testObject).whenComplete((unused, throwable) -> {
                                application.getLogger().log(ConsoleColor.YELLOW + "saved entry!");

                                application.getLogger().log(ConsoleColor.GREEN + "Took " + (System.currentTimeMillis() - startTime) + "ms to run this jedis command.");
                            });
                        } else {
                            application.getLogger().log("no");
                        }
                        break;
                    case "fetch":
                        if (commandArgs.length == 2) {
                            storageLayerControllerLayer.fetchEntryByKey(commandArgs[1]).whenComplete((testObject1, throwable) -> {
                                if (testObject1 != null) {
                                    application.getLogger().log(ConsoleColor.YELLOW + "fetched entry! result: " + testObject1.getTest());
                                } else {
                                    application.getLogger().log(ConsoleColor.RED + "I couldn't find that entry!");
                                }

                                application.getLogger().log(ConsoleColor.GREEN + "Took " + (System.currentTimeMillis() - startTime) + "ms to run this jedis command.");
                            });
                        } else {
                            application.getLogger().log(ConsoleColor.RED + "no");
                        }
                        break;
                    case "fetchall":
                        storageLayerControllerLayer.fetchAllEntries().whenComplete((entries, throwable) -> {
                            if (entries == null || entries.isEmpty()) {
                                application.getLogger().log(ConsoleColor.RED + "There aren't any entries!");
                            } else {
                                final AtomicInteger atomicInteger = new AtomicInteger();

                                entries.forEach((s, testObject1) -> {
                                    application.getLogger().log(ConsoleColor.CYAN + atomicInteger.incrementAndGet() + ". " + s + " - " + testObject1.getTest());
                                });
                            }

                            application.getLogger().log(ConsoleColor.GREEN + "Took " + (System.currentTimeMillis() - startTime) + "ms to run this jedis command.");
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

        this.getStorageLayerController().registerLayer("hello", testObjectRedisStorageLayer);

        this.getLogger().log(ConsoleColor.YELLOW + "Initialized redis storage layer, now waiting for commands...");
    }
}
