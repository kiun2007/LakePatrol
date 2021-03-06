package com.province.libcacheline.data.getter;

import android.content.Intent;
import android.os.Parcelable;

import com.province.libcacheline.data.FieldGetter;

import java.lang.reflect.Type;

public class IntentGetter extends FieldGetter<Intent> {

    public IntentGetter(Intent value) {
        super(value);
    }

    @Override
    public Object getValue(String fieldName, Type type) {

        if (type.equals(String.class)){
            return value.getStringExtra(fieldName);
        }else if (type.equals(Integer.class)){
            return value.getIntExtra(fieldName, 0);
        }else if (type.equals(Double.class)){
            return value.getDoubleExtra(fieldName, 0);
        }else if (type.equals(Float.class)){
            return value.getFloatExtra(fieldName, 0);
        }else if (type.equals(Long.class)){
            return value.getLongExtra(fieldName, 0);
        }else if (type.equals(Short.class)){
            return value.getShortExtra(fieldName, (short) 0);
        }else if (type.equals(Boolean.class)){
            return value.getBooleanExtra(fieldName, false);
        }else if (type.equals(Byte.class)){
            return value.getByteExtra(fieldName, (byte) 0);
        }else if ((type instanceof Class) && ((Class) type).isAssignableFrom(Parcelable.class)){
            return value.getParcelableExtra(fieldName);
        }
        return null;
    }
}
