package com.solexgames.datastore.commons.connection.impl.mongo;

import com.mongodb.MongoClient;
import com.solexgames.datastore.commons.connection.impl.MongoConnection;

/**
 * @author GrowlyX
 * @since 9/8/2021
 */
public class LocalMongoConnection extends MongoConnection {

    @Override
    public MongoClient getClient() {
        return new MongoClient();
    }
}
