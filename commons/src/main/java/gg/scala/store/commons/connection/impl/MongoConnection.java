package gg.scala.store.commons.connection.impl;

import com.mongodb.MongoClient;
import gg.scala.store.commons.connection.Connection;

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
