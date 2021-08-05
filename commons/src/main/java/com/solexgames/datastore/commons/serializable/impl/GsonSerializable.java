package com.solexgames.datastore.commons.serializable.impl;

import com.solexgames.datastore.commons.platform.DataStorePlatforms;
import com.solexgames.datastore.commons.serializable.Serializable;
import lombok.RequiredArgsConstructor;

/**
 * @author GrowlyX
 * @since 8/4/2021
 */

@RequiredArgsConstructor
public class GsonSerializable<T> implements Serializable<T, String> {

    private final Class<T> clazz;

    @Override
    public String serialize(T t) {
        return DataStorePlatforms.current().getGson().toJson(t);
    }

    @Override
    public T deserialize(String s) {
        return DataStorePlatforms.current().getGson().fromJson(s, this.getClazz());
    }

    @Override
    public Class<T> getClazz() {
        return this.clazz;
    }
}
