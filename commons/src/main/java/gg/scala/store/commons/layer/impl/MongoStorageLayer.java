package gg.scala.store.commons.layer.impl;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import gg.scala.store.commons.connection.impl.MongoConnection;
import gg.scala.store.commons.layer.AbstractStorageLayer;
import gg.scala.store.commons.serializable.impl.GsonSerializable;
import lombok.Getter;
import org.bson.Document;
import org.bson.conversions.Bson;
import redis.clients.jedis.Jedis;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.CompletableFuture;

/**
 * @author GrowlyX
 * @since 8/4/2021
 * <p>
 * An easy-to-use object based wrapper
 * for {@link Jedis}'s key-value storage system.
 */

@Getter
public class MongoStorageLayer<T> extends AbstractStorageLayer<String, T>  {

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
                    new Document(
                            "$set",
                            Document.parse(serialize(t))
                    ),
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
        return CompletableFuture.supplyAsync(() -> this.fetchEntryByKeySync(s));
    }

    public T fetchEntryByKeySync(String s) {
        final Document document = this.collection.find(Filters.eq("_id", s)).first();

        if (document == null) {
            return null;
        }

        return deserialize(document.toJson());
    }

    @Override
    public CompletableFuture<Map<String, T>> fetchAllEntries() {
        return CompletableFuture.supplyAsync(this::fetchAllEntriesSync);
    }

    public Map<String, T> fetchAllEntriesSync() {
        final Map<String, T> entries = new WeakHashMap<>();

        for (Document document : this.collection.find()) {
            entries.put(document.getString("_id"), deserialize(document.toJson()));
        }

        return entries;
    }

    public CompletableFuture<Map<String, T>> fetchAllEntriesWithFilter(Bson filter) {
        return CompletableFuture.supplyAsync(() -> {
            final Map<String, T> entries = new WeakHashMap<>();

            for (Document document : this.collection.find(filter)) {
                entries.put(document.getString("_id"), deserialize(document.toJson()));
            }

            return entries;
        });
    }

    public T deserialize(String json)
    {
        return this.serializable.deserialize(json);
    }

    public String serialize(T t)
    {
        return this.serializable.serialize(t);
    }

    @Override
    public Class<T> getType() {
        return this.type;
    }

}
