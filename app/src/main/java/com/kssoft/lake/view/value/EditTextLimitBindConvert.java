package com.kssoft.lake.view.value;

import android.app.Activity;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;

import kiun.com.bvroutine.base.binding.value.EditTextBindConvert;

public class EditTextLimitBindConvert extends EditTextBindConvert implements View.OnFocusChangeListener {

    public static final String LimitCount = "EditTextLimitBindConvert_Limit";

    private int editCount = 0;

    private int limitCount = Integer.MAX_VALUE;

    public EditTextLimitBindConvert(AppCompatEditText view) {
        super(view);
        view.setOnFocusChangeListener(this);
        if (view.getContext() instanceof Activity){
            limitCount = ((Activity) view.getContext()).getIntent().getIntExtra(LimitCount, Integer.MAX_VALUE);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus){
            editCount ++;

            if (editCount >= limitCount){
                v.setEnabled(false);
            }
        }
    }
}
