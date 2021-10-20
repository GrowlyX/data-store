package gg.scala.store.commons.connection.impl.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import gg.scala.store.commons.connection.impl.MongoConnection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author GrowlyX
 * @since 9/8/2021
 */
@Getter
@RequiredArgsConstructor
public class UriMongoConnection extends MongoConnection {

    private final String uri;

    private final MongoClient client = new MongoClient(
            new MongoClientURI(this.uri)
    );

}
