package com.solexgames.datastore.commons.connection.impl;

import com.mongodb.MongoClient;
import com.solexgames.datastore.commons.connection.Connection;

/**
 * @author GrowlyX
 * @since 9/8/2021
 */
public abstract class MongoConnection implements Connection<MongoClient> {

    @Override
    public MongoClient getConnection() {
        return this.getClient();
    }

    public abstract MongoClient getClient();

}
