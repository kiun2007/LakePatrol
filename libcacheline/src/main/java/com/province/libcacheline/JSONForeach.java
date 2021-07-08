package com.province.libcacheline;

@FunctionalInterface
public interface JSONForeach<E> {
    boolean itemCall(String key, E value);
}
