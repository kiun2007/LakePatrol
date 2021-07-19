package com.kssoft.lake.ui.activity.list;

import com.kssoft.lake.R;
import com.kssoft.lake.databinding.ActivityListSamplingDetailsBinding;
import com.kssoft.lake.net.requests.dto.DataDto;
import com.kssoft.lake.net.services.DataService;

import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine_apt.IntentValue;

public class ListSamplingActivity extends RequestBVActivity<ActivityListSamplingDetailsBinding> {
    public static final Class clz = ListSamplingActivity.class;

    @IntentValue
    String recordId;

    @IntentValue
    String xctp;

    public int getViewId() {
        return R.layout.activity_list_sampling_details;
    }

    public void initView() {

        DataDto dataDto = new DataDto();
        dataDto.setXctp(this.xctp);
        dataDto.setRdcd(this.recordId);

        rbp.addRequest(()->rbp.callServiceData(DataService.class, s -> s.dataAList(dataDto)), binding::setList);
    }
}