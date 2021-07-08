package com.province.libcacheline.data.getter;

import com.province.libcacheline.data.FieldGetter;

import java.lang.reflect.Type;
import java.util.Map;

public class MapGetter extends FieldGetter<Map> {

    public MapGetter(Map value) {
        super(value);
    }

    @Override
    public Object getValue(String fieldName, Type type) {
        return value.get(fieldName);
    }
}
