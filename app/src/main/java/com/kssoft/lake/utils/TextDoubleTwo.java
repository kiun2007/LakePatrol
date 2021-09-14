package com.kssoft.lake.utils;

import android.annotation.SuppressLint;

import kiun.com.bvroutine.base.binding.convert.TypeConvert;

public class TextDoubleTwo extends TypeConvert<String, Double> {
    @Override
    protected Double convert(String s) {
        return Double.parseDouble(s);
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected String convertFrom(Double aDouble) {
        return String.format("%.2f",aDouble);
    }
}
