package com.province.libcacheline.body;

import okhttp3.RequestBody;

public abstract class BaseBodyBuilder<T> {

    public abstract RequestBody convert(T value);

    public RequestBody getBody(Object value){
        return convert((T) value);
    }
}
