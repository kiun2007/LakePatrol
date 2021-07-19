package com.kssoft.lake.ui.activity.personnel;

import com.kssoft.lake.BR;
import com.kssoft.lake.R;
import com.kssoft.lake.data.SamplingBase;
import com.kssoft.lake.databinding.ActivitySamplingDetailsBinding;
import com.kssoft.lake.net.requests.dto.DataDto;
import com.kssoft.lake.net.responses.vo.StationSampling;
import com.kssoft.lake.net.services.DataService;
import java.util.List;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.utils.ListUtil;
import kiun.com.bvroutine_apt.ActivityOpen;
import kiun.com.bvroutine_apt.IntentValue;

/**
 * 文 件 名: SamplingDetails
 * 作 者: 刘春杰
 * 创建日期: 2020/8/5 10:10
 * 说明: 采样详情
 */
public class SamplingDetailsActivity extends RequestBVActivity<ActivitySamplingDetailsBinding> {

    public static final Class clz = SamplingDetailsActivity.class;

    @IntentValue
    String stcd;

    @IntentValue
    String tkcd;

    @IntentValue
    String xtcp;

    DataDto dataDto;

    SamplingBase samplingBase;

    @Override
    public int getViewId() {
        if (stcd != null && xtcp != null){
            dataDto = new DataDto(stcd, xtcp, tkcd);
        }
        return R.layout.activity_sampling_details;
    }

    @ActivityOpen
    public void openByDetails(SamplingBase sampling){
//        samplingBase = sampling;
        String tkcd = ListUtil.isEmpty(sampling.getXcTaskP()) ? null : sampling.getXcTaskP().get(0).getTkcd();
        dataDto = new DataDto(sampling.getStcd(), sampling.type(), tkcd);
    }

    @Override
    public void initView() {

        if (samplingBase != null){
            samplingBase.setExpansion(true);
            setVariable(BR.data, samplingBase);
            return;
        }
        addRequest(this::getSamplingValue, v->{
            if (v != null){
                v.setExpansion(true);
                setVariable(BR.data, v);
            }
        });
    }

    private StationSampling getSamplingValue() throws Exception{
        List<StationSampling> list = rbp.callServiceData(DataService.class, s->s.dataAList(dataDto));
        return ListUtil.first(list);
    }
}