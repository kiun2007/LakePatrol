package com.kssoft.lake.view.value;

import android.app.Activity;
import android.widget.RadioGroup;

import kiun.com.bvroutine.base.binding.value.RadioGroupBindConvert;

import static com.kssoft.lake.view.value.EditTextLimitBindConvert.LimitCount;

public class RadioGroupLimitBindConvert extends RadioGroupBindConvert {

    private int limitCount = Integer.MAX_VALUE;
    private int editCount = 0;

    public RadioGroupLimitBindConvert(RadioGroup view) {
        super(view);
        if (view.getContext() instanceof Activity){
            limitCount = ((Activity) view.getContext()).getIntent().getIntExtra(LimitCount, Integer.MAX_VALUE);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        super.onCheckedChanged(group, checkedId);
        if (editCount >= limitCount){
            group.setEnabled(false);
        }
        editCount++;
    }
}
