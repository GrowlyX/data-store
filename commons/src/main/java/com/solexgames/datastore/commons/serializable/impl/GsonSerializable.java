package com.solexgames.datastore.commons.serializable.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.solexgames.datastore.commons.serializable.Serializable;
import lombok.RequiredArgsConstructor;

/**
 * @author GrowlyX
 * @since 8/4/2021
 */

@RequiredArgsConstructor
public class GsonSerializable<T> implements Serializable<T, String> {

    public Gson gson = new GsonBuilder()
            .setLongSerializationPolicy(LongSerializationPolicy.STRING)
            .create();

    private final Class<T> clazz;

    @Override
    public String serialize(T t) {
        return this.gson.toJson(t);
    }

    @Override
    public T deserialize(String s) {
        return this.gson.fromJson(s, this.getClazz());
    }

    @Override
    public Class<T> getClazz() {
        return this.clazz;
    }
}
