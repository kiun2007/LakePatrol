package com.kssoft.lake.ui.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;

import com.kssoft.lake.R;
import com.kssoft.lake.data.SamplingBase;
import com.kssoft.lake.databinding.FragmentSamplingCheckBinding;
import com.kssoft.lake.net.requests.dto.DataDto;
import com.kssoft.lake.ui.activity.personnel.SamplingDetailsActivityHandler;
import com.kssoft.lake.utils.ListViewUtil;

import kiun.com.bvroutine.BR;
import kiun.com.bvroutine.base.BVBaseActivity;
import kiun.com.bvroutine.base.RequestBVFragment;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.views.adapter.PagerFragmentAdapter;

public class SamplingCheckFragment extends RequestBVFragment<FragmentSamplingCheckBinding> {

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

    @Override
    public int getViewId() {
        return R.layout.fragment_sampling_check;
    }

    @Override
    public void initView() {
        DataDto dataDto = new DataDto();
        dataDto.setXctp(String.valueOf(getArguments().getInt(PagerFragmentAdapter.INDEX)));

        mViewBinding.setDataDto(dataDto);
        mViewBinding.setHandler(listHandler);
    }
}
