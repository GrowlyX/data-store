# Data Store
An easy-to-use wrapper for redis' cached storage system. (support for more data types coming soon)

## Usage:
Data Store is very simple, here's an example for the redis wrapper:

Create your application class, this should extend the `DataStorePlatform` available in the commons module.
```java
public class NewTestApplication extends DataStorePlatform {

    public static void main(String[] args) {
        new NewTestApplication().initalizeLayer();
    }
    
    public void initalizeLayer() {
        
    }

    /**
     * the logger type to use for this app
     */
    @Override
    public ConsoleLogger getLogger() {
        return new SimpleConsoleLogger();
    }
}
```

You can now begin with definining a `RedisConnection` type.

Available types for redis:
- AuthRedisConnection
- NoAuthRedisConnection

For this example, i'll be using `NoAuthRedisConnection` as i'm using my local redis server.
```java
    public void initalizeLayer() {
        final RedisConnection noAuthRedisConnection = new NoAuthRedisConnection("127.0.0.1", 6379);
        
    }
```

You can now initialize a new `RedisStorageBuilder<YourObject>`, with YourObject being the object you want to serialize.
```java
    public void initalizeLayer() {
        final RedisConnection noAuthRedisConnection = new NoAuthRedisConnection("127.0.0.1", 6379);
        final RedisStorageBuilder<TestObject> testClassRedisStorageBuilder = new RedisStorageBuilder<>();
        
    }
```

Finally, you can build your `RedisStorageLayer<YourObject>` instance by using the chain methods provided in `RedisStorageBuilder`.
```java
    public void initalizeLayer() {
        final RedisConnection noAuthRedisConnection = new NoAuthRedisConnection("127.0.0.1", 6379);
        final RedisStorageBuilder<TestObject> testClassRedisStorageBuilder = new RedisStorageBuilder<>();
        final RedisStorageLayer<TestObject> testObjectRedisStorageLayer = testClassRedisStorageBuilder
                .setSection("datastore_test")
                .setType(TestObject.class)
                .setConnection(noAuthRedisConnection)
                .build();

    }
```

**OPTIONAL:** Want to access your layer from a seperate class, or at another time? You can use the provided `StorageLayerController` instance in your application class to store your layer for use somewhere else.
```java
    public void initalizeLayer() {
        final RedisConnection noAuthRedisConnection = new NoAuthRedisConnection("127.0.0.1", 6379);
        final RedisStorageBuilder<TestObject> testClassRedisStorageBuilder = new RedisStorageBuilder<>();
        final RedisStorageLayer<TestObject> testObjectRedisStorageLayer = testClassRedisStorageBuilder
                .setSection("datastore_test")
                .setType(TestObject.class)
                .setConnection(noAuthRedisConnection)
                .build();

        this.getStorageLayerController().registerLayer("testya", testObjectRedisStorageLayer);
    }
```

```java
        // how to fetch the layer
        this.getStorageLayerController().getLayer("hello", RedisStorageLayer.class);
```

**Congrats! You've setup your layer! You can now use these awesome methods to store/fetch data from your database!**

```java
    public CompletableFuture<Void> saveEntry(K k, V v);

    public CompletableFuture<Void> deleteEntry(K k);

    public CompletableFuture<V> fetchEntryByKey(K k);

    public CompletableFuture<Map<K, V>> fetchAllEntries();
```
