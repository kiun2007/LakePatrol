package com.kssoft.lake.ui.fragment;

import android.content.Context;

import com.kssoft.lake.R;
import com.kssoft.lake.databinding.FragmentSamplingRecordBinding;
import com.kssoft.lake.net.requests.dto.RecdDto;
import com.kssoft.lake.net.requests.dto.XcRecdR;
import com.kssoft.lake.net.services.ListService;
import com.kssoft.lake.ui.activity.list.ListSamplingActivityHandler;
import com.kssoft.lake.ui.activity.personnel.MapTrailActivityHandler;

import java.util.List;

import kiun.com.bvroutine.BR;
import kiun.com.bvroutine.base.RequestBVFragment;
import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.net.ServiceGenerator;
import kiun.com.bvroutine.presenters.list.NetListProvider;
import kiun.com.bvroutine.utils.RetrofitUtil;
import kiun.com.bvroutine.views.adapter.PagerFragmentAdapter;

public class SamplingRecordFragment extends RequestBVFragment<FragmentSamplingRecordBinding> {

    RecdDto dataDto = new RecdDto();

    ListHandler<XcRecdR> listHandler = new ListHandler<XcRecdR>(BR.handler, R.layout.list_error_normal) {
        public void onClick(Context context, int tag, XcRecdR xcRecdR) {

            if (tag == 1) {
                ListSamplingActivityHandler.openActivityNormal(context, xcRecdR.getRdcd(), xcRecdR.getXctp());
            } else {
                if (tag == 2) {
                    MapTrailActivityHandler.openActivityNormal(context, xcRecdR.getRdcd());
                }
            }
        }
    };

    private List<? extends RecdDto> getRecordList(PagerBean pagerBean) throws Exception{
        dataDto.inherit(pagerBean);
        int index = getArguments().getInt(PagerFragmentAdapter.INDEX);
        if (index == 0){
            List<RecdDto> lakeRList = RetrofitUtil.callServiceList(ListService.class, s-> s.getRecordList(dataDto),pagerBean, ServiceGenerator::createService);
            return lakeRList;
        }else {
            List<RecdDto> generalList = RetrofitUtil.callServiceList(ListService.class, s-> s.getRecordList(dataDto),pagerBean, ServiceGenerator::createService);
            return generalList;
        }
    }

    @Override
    public int getViewId() {
        return R.layout.fragment_sampling_record;
    }

    @Override
    public void initView() {
        int index = getArguments().getInt(PagerFragmentAdapter.INDEX);
        dataDto.setRdst(String.valueOf(index));

        NetListProvider listProvider = NetListProvider.create(getContext(), listHandler, R.layout.item_inspection);
        listProvider.setCaller(this::getRecordList);
        mViewBinding.setProvider(listProvider);
    }
}
