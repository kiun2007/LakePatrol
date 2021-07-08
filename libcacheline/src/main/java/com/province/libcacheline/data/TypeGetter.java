package com.province.libcacheline.data;

import com.province.libcacheline.callers.KeyValueGet;

import java.util.HashMap;
import java.util.Map;

public class TypeGetter {

    private Map<Class,KeyValueGet> allPuts = new HashMap<>();

    public <T>TypeGetter addGetter(Class<T> clz, KeyValueGet<T> getter){
        allPuts.put(clz, getter);
        return this;
    }

    public Object execute(String key, Class clz){
        KeyValueGet valuePut = allPuts.get(clz);
        if (valuePut != null){
            return valuePut.get(key);
        }
        return null;
    }
}