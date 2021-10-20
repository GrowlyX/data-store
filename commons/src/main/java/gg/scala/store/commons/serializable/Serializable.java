package gg.scala.store.commons.serializable;

/**
 * @author GrowlyX
 * @since 8/4/2021
 */

public interface Serializable<U, V> {

    V serialize(U u);

    U deserialize(V v);

    Class<U> getClazz();

}
