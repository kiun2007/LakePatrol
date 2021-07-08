package com.province.libcacheline.callers;

@FunctionalInterface
public interface KeyValuePut<T>{
    void put(String key,T value);
}
