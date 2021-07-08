package com.kssoft.lake.ui.activity.personnel;

import com.kssoft.lake.R;
import com.kssoft.lake.databinding.ActivityMapTrailBinding;
import com.kssoft.lake.net.requests.dto.RecdDto;
import com.kssoft.lake.net.services.RecordService;
import com.kssoft.lake.ui.activity.GisMapActivity;

import kiun.com.bvroutine_apt.IntentValue;

/**
 * 文 件 名: MapTrail
 * 作 者: 刘春杰
 * 创建日期: 2020/8/18 21:12
 * 说明: 巡查记录
 */
public class MapTrailActivity extends GisMapActivity<ActivityMapTrailBinding> {

    public static final Class clz = MapTrailActivity.class;

    @IntentValue
    String recordId;

    @Override
    public int getViewId() {
        return R.layout.activity_map_trail;
    }

    @Override
    public void initView() {
        getIntent().putExtra("arcGisShow", true);
        addRequest(()->rbp.callServiceList(RecordService.class, s -> s.getTrailList(new RecdDto(){{setRdcd(recordId);}}),null), binding::setTrailList);
    }
}