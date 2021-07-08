package com.province.libcacheline.data.getter;

import com.province.libcacheline.data.FieldGetter;

import org.json.JSONObject;

import java.lang.reflect.Type;

public class JSONGetter extends FieldGetter<JSONObject> {

    public JSONGetter(JSONObject value) {
        super(value);
    }

    @Override
    public Object getValue(String fieldName, Type type) {
        return value.opt(fieldName);
    }
}
