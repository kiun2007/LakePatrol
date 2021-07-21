package com.kssoft.lake.ui.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;

import com.kssoft.lake.R;
import com.kssoft.lake.data.SamplingBase;
import com.kssoft.lake.data.model.commit.SamplingGeneral;
import com.kssoft.lake.data.model.commit.XcLakeR;
import com.kssoft.lake.databinding.FragmentSamplingCheckBinding;
import com.kssoft.lake.net.requests.dto.DataDto;
import com.kssoft.lake.net.services.DataService;
import com.kssoft.lake.ui.activity.personnel.SamplingDetailsActivityHandler;
import com.kssoft.lake.utils.ListViewUtil;

import java.util.List;

import kiun.com.bvroutine.BR;
import kiun.com.bvroutine.base.BVBaseActivity;
import kiun.com.bvroutine.base.RequestBVFragment;
import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.interfaces.callers.PagerCaller;
import kiun.com.bvroutine.presenters.list.NetListProvider;
import kiun.com.bvroutine.utils.ListUtil;
import kiun.com.bvroutine.utils.RetrofitUtil;
import kiun.com.bvroutine.views.adapter.PagerFragmentAdapter;

public class SamplingCheckFragment extends RequestBVFragment<FragmentSamplingCheckBinding> {

    DataDto dataDto = new DataDto();

    ListHandler<SamplingBase> listHandler = new ListHandler<SamplingBase>(BR.handler, R.layout.list_error_normal) {

        public void onClick(Context context, int tag, SamplingBase samplingBase) {

            BVBaseActivity activity = (BVBaseActivity) context;

            if (tag == 0) {
                SamplingDetailsActivityHandler.openByDetails(context, samplingBase);
            } else {
                if (tag == 1) {

                    new AlertDialog.Builder(context)
                            .setTitle("提示")
                            .setMessage("是否对数据进行校核,您需将站点数据重新填报一次")
                            .setNegativeButton("取消", null)
                            .setPositiveButton("确定校核", (dialog, which) -> {
                                Intent intent = new Intent(context, samplingBase.commitActivity()).putExtra("sampling", samplingBase).putExtra("isCheck", true);
                                activity.startForResult(intent, v -> {
                                    ListViewUtil.refresh(this);
                                });
                            }).show();
                }

            }
        }
    };

    private List<? extends SamplingBase> getSamplingList(PagerBean pagerBean) throws Exception{

        dataDto.inherit(pagerBean);
        int index = getArguments().getInt(PagerFragmentAdapter.INDEX);

        if (index == 0){
            List<XcLakeR> lakeRList = RetrofitUtil.callServiceList(DataService.class, s-> s.dataLakeList(dataDto));
            return lakeRList;
        }else {
            List<SamplingGeneral> generalList = RetrofitUtil.callServiceList(DataService.class, s-> s.dataGeneralList(dataDto));
            ListUtil.map(generalList, item-> item.setXctp(String.valueOf(index)));
            return generalList;
        }
    }

    @Override
    public int getViewId() {
        return R.layout.fragment_sampling_check;
    }

    @Override
    public void initView() {

        int index = getArguments().getInt(PagerFragmentAdapter.INDEX);
        dataDto.setXctp(String.valueOf(index));

        NetListProvider listProvider = NetListProvider.create(getContext(), listHandler, R.layout.item_sampling_check);
        listProvider.setCaller(this::getSamplingList);
        mViewBinding.setProvider(listProvider);
    }
}
