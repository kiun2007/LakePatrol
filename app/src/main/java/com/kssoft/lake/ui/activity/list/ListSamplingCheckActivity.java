//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.kssoft.lake.ui.activity.list;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;

import com.kssoft.lake.BR;
import com.kssoft.lake.R;
import com.kssoft.lake.data.SamplingBase;
import com.kssoft.lake.databinding.ActivityListSamplingCheckBinding;
import com.kssoft.lake.net.requests.dto.DataDto;
import com.kssoft.lake.ui.activity.personnel.SamplingDetailsActivityHandler;
import com.kssoft.lake.utils.ListViewUtil;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.data.FieldEvent;
import kiun.com.bvroutine.handlers.ListHandler;

public class ListSamplingCheckActivity extends RequestBVActivity<ActivityListSamplingCheckBinding> {

    public static final Class clz = ListSamplingCheckActivity.class;

    public ListSamplingCheckActivity() {
    }

    public int getViewId() {
        return R.layout.activity_list_sampling_check;
    }

    public void initView() {
        setVariable(BR.index, new FieldEvent<>(0));
    }
}
