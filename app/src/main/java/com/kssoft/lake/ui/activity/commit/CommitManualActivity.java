package com.kssoft.lake.ui.activity.commit;

import com.kssoft.lake.R;
import com.kssoft.lake.data.SamplingBase;
import com.kssoft.lake.data.model.commit.XcWrrbR;
import com.kssoft.lake.databinding.ActivityCommitObservationManualBinding;

import kiun.com.bvroutine.base.EventBean;
import kiun.com.bvroutine_apt.ActivityOpen;
import kiun.com.bvroutine_apt.IntentValue;

/**
 * 文 件 名: CommitManual
 * 作 者: 刘春杰
 * 创建日期: 2020/8/6 18:35
 * 说明: 人工监测
 */
public class CommitManualActivity extends CommitBaseActivity<ActivityCommitObservationManualBinding> {

    public static final Class clz = CommitManualActivity.class;

    @IntentValue
    String recordId;

    @Override
    public int getViewId() {
        return R.layout.activity_commit_observation_manual;
    }

    @Override
    protected SamplingBase getSampling() {
        if (recordId == null){
            return null;
        }
        return new XcWrrbR(recordId);
    }

    @Override
    protected EventBean saveBean() {
        return binding.getData();
    }

    @Override
    protected String getXctp() {
        return "2";
    }

    @Override
    @ActivityOpen
    protected void startSampling(SamplingBase base) {
        samplingBase = base;
    }
}