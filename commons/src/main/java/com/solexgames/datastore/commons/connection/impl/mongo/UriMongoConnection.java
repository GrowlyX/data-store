package com.solexgames.datastore.commons.connection.impl.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.solexgames.datastore.commons.connection.impl.MongoConnection;
import lombok.RequiredArgsConstructor;

/**
 * @author GrowlyX
 * @since 9/8/2021
 */
@RequiredArgsConstructor
public class UriMongoConnection extends MongoConnection {

    private final String uri;

    @Override
    public MongoClient getClient() {
        return new MongoClient(
                new MongoClientURI(this.uri)
        );
    }
}
