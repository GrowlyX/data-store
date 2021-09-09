package com.solexgames.datastore.commons.layer.impl;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.solexgames.datastore.commons.connection.impl.MongoConnection;
import com.solexgames.datastore.commons.connection.impl.RedisConnection;
import com.solexgames.datastore.commons.connection.impl.redis.AuthRedisConnection;
import com.solexgames.datastore.commons.layer.AbstractStorageLayer;
import com.solexgames.datastore.commons.serializable.impl.GsonSerializable;
import lombok.Getter;
import org.bson.Document;
import org.bson.conversions.Bson;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * @author GrowlyX
 * @since 8/4/2021
 * <p>
 * An easy-to-use object based wrapper
 * for {@link Jedis}'s key-value storage system.
 */

@Getter
public class MongoStorageLayer<T>  extends AbstractStorageLayer<String, T>  {

    private final GsonSerializable<T> serializable;

    private final MongoConnection mongoConnection;
    private final MongoCollection<Document> collection;

    private final Class<T> type;

    public MongoStorageLayer(
            final MongoConnection mongoConnection,
            final String database, final String collection, final Class<T> tClass
    ) {
        this.collection = mongoConnection.getConnection()
                .getDatabase(database)
                .getCollection(collection);

        this.type = tClass;
        this.mongoConnection = mongoConnection;

        this.serializable = new GsonSerializable<>(tClass);
    }

    public void supplyWithCustomGson(Gson gson) {
        this.serializable.gson = gson;
    }

    @Override
    public CompletableFuture<Void> saveEntry(String s, T t) {
        return CompletableFuture.runAsync(() -> {
            this.collection.updateOne(
                    Filters.eq("_id", s),
                    new Document("$set", Document.parse(this.serializable.serialize(t))),
                    new UpdateOptions().upsert(true)
            );
        });
    }

    @Override
    public CompletableFuture<Void> deleteEntry(String s) {
        return CompletableFuture.runAsync(() -> this.collection.deleteOne(Filters.eq("_id", s)));
    }

    @Override
    public CompletableFuture<T> fetchEntryByKey(String s) {
        return CompletableFuture.supplyAsync(() -> {
            final Document document = this.collection.find(Filters.eq("_id", s)).first();

            if (document == null) {
                return null;
            }

            return this.serializable.deserialize(document.toJson());
        });
    }

    @Override
    public CompletableFuture<Map<String, T>> fetchAllEntries() {
        return CompletableFuture.supplyAsync(() -> {
            final Map<String, T> entries = new HashMap<>();

            for (Document document : this.collection.find()) {
                entries.put(document.getString("_id"), this.serializable.deserialize(document.toJson()));
            }

            return entries;
        });
    }

    public CompletableFuture<Map<String, T>> fetchAllEntriesWithFilter(Bson filter) {
        return CompletableFuture.supplyAsync(() -> {
            final Map<String, T> entries = new HashMap<>();

            for (Document document : this.collection.find(filter)) {
                entries.put(document.getString("_id"), this.serializable.deserialize(document.toJson()));
            }

            return entries;
        });
    }

    @Override
    public Class<T> getType() {
        return this.type;
    }

}
