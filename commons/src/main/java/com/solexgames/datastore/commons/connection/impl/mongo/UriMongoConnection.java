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
    
    private MongoClient client;
    
    {
        this.client = new MongoClient(
                new MongoClientURI(this.uri)
        );
    }

    @Override
    public MongoClient getClient() {
        return this.client
    }
}
