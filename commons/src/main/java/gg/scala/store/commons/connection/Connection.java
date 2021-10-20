package gg.scala.store.commons.connection;

/**
 * @author GrowlyX
 * @since 8/6/2021
 */

public interface Connection<T> {

    /**
     * A connection object that the bound layer type can use.
     *
     * @return the connection object which is of type {@link T}
     */
    T getConnection();

}
