package com.solexgames.datastore.commons.connection.impl.mongo;

import com.mongodb.MongoClient;
import com.solexgames.datastore.commons.connection.impl.MongoConnection;
import lombok.Getter;

/**
 * @author GrowlyX
 * @since 9/8/2021
 */
@Getter
public class LocalMongoConnection extends MongoConnection {

    private final MongoClient client = new MongoClient();

}
