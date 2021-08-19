package com.kssoft.lake.ui.activity.list;

import com.kssoft.lake.BR;
import com.kssoft.lake.R;
import com.kssoft.lake.databinding.ActivityListSamplingRecordBinding;

import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.data.FieldEvent;

public class ListSamplingRecordActivity extends RequestBVActivity<ActivityListSamplingRecordBinding> {

    public static final Class clz = ListSamplingRecordActivity.class;

    @Override
    public int getViewId() {
        return R.layout.activity_list_sampling_record;
    }

    @Override
    public void initView() {
        setVariable(BR.index, new FieldEvent<>(0));
    }
}
