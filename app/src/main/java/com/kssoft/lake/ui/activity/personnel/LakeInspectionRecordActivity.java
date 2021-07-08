package com.kssoft.lake.ui.activity.personnel;

import android.content.Context;

import com.kssoft.lake.R;
import com.kssoft.lake.databinding.ActivityLakeInspectionRecordBinding;
import com.kssoft.lake.net.requests.dto.RecdDto;
import com.kssoft.lake.net.services.ListService;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine_apt.IntentValue;

/**
 * 文 件 名: LakeInspectionRecord
 * 作 者: 刘春杰
 * 创建日期: 2020/8/3 16:03
 * 说明: 记录详情
 */
public class LakeInspectionRecordActivity extends RequestBVActivity<ActivityLakeInspectionRecordBinding> {

    public static final Class clz = LakeInspectionRecordActivity.class;

    @IntentValue
    String rdcd;

    @Override
    public int getViewId() {
        return R.layout.activity_lake_inspection_record;
    }

    @Override
    public void initView() {

        RecdDto recdDto = new RecdDto();
        recdDto.setRdcd(rdcd);
        addRequest(()->rbp.callServiceList(ListService.class, s -> s.getRecordList(recdDto), null), v -> {
            int a = 0;
        });
    }

    @Override
    public Context getContext() {
        return this;
    }
}