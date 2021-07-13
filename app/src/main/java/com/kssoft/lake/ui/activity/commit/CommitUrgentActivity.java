package com.kssoft.lake.ui.activity.commit;

import android.text.TextUtils;

import com.kssoft.lake.R;
import com.kssoft.lake.data.SamplingBase;
import com.kssoft.lake.data.model.commit.XcWqnmispR;
import com.kssoft.lake.databinding.ActivityCommitObservationUrgentBinding;
import kiun.com.bvroutine.base.EventBean;
import kiun.com.bvroutine.utils.SharedUtil;
import kiun.com.bvroutine_apt.ActivityOpen;
import kiun.com.bvroutine_apt.IntentValue;

/**
 * 文 件 名: CommitUrgent
 * 作 者: 刘春杰
 * 创建日期: 2020/8/6 18:39
 * 说明: 紧急监测
 */
public class CommitUrgentActivity extends CommitBaseActivity<ActivityCommitObservationUrgentBinding> {

    public static final Class clz = CommitUrgentActivity.class;

    @IntentValue
    String recordId;

    @Override
    public void initView() {
        isNoPlan = SharedUtil.getValue("isPlan", false);
        super.initView();
    }

    @Override
    public int getViewId() {
        return R.layout.activity_commit_observation_urgent;
    }

    @Override
    protected SamplingBase getSampling() {
        if (recordId == null){
            return null;
        }
        return new XcWqnmispR(recordId);
    }

    @Override
    protected EventBean saveBean() {
        return binding.getData();
    }

    @Override
    protected String getXctp() {
        return "3";
    }

    @Override
    protected void onSamplingChanged(SamplingBase samplingBase) {
//        if (isNoPlan == true) {
//            super.onSamplingChanged(samplingBase);
//        }else {
            if (TextUtils.isEmpty(samplingBase.getStcd()) || !isNoPlan){
                super.onSamplingChanged(samplingBase);
            }
//        }
    }

    @Override
    @ActivityOpen
    protected void startSampling(SamplingBase base) {
        samplingBase = base;
    }
}