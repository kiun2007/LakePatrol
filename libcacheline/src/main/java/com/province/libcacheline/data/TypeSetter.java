package com.province.libcacheline.data;

import com.province.libcacheline.callers.KeyValuePut;

import java.util.HashMap;
import java.util.Map;

public class TypeSetter {
    private Map<Class,KeyValuePut> allPuts = new HashMap<>();

    public <T>TypeSetter addPutter(Class<T> clz, KeyValuePut<T> putter){
        allPuts.put(clz, putter);
        return this;
    }

    public void execute(String key, Object value){
        KeyValuePut valuePut = allPuts.get(value.getClass());
        if (valuePut != null){
            valuePut.put(key, value);
        }
    }
}
