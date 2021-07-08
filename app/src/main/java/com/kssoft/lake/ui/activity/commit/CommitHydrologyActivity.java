package com.kssoft.lake.ui.activity.commit;

import com.kssoft.lake.R;
import com.kssoft.lake.data.SamplingBase;
import com.kssoft.lake.data.model.commit.XcRiverR;
import com.kssoft.lake.databinding.ActivityCommitHydrologyBinding;
import kiun.com.bvroutine.base.EventBean;
import kiun.com.bvroutine_apt.ActivityOpen;
import kiun.com.bvroutine_apt.IntentValue;

/**
 * 文 件 名: CommitHydrology
 * 作 者: 刘春杰
 * 创建日期: 2020/8/5 22:11
 * 说明: 水文巡测填报
 */
public class CommitHydrologyActivity extends CommitBaseActivity<ActivityCommitHydrologyBinding> {

    public static final Class clz = CommitHydrologyActivity.class;

    @IntentValue
    String recordId;

    @Override
    public int getViewId() {
        return R.layout.activity_commit_hydrology;
    }

    @Override
    protected SamplingBase getSampling() {
        if (recordId == null){
            return null;
        }
        return new XcRiverR(recordId);
    }

    @Override
    protected EventBean saveBean() {
        return binding.getData();
    }

    @Override
    protected String getXctp() {
        return "1";
    }

    @Override
    @ActivityOpen
    protected void startSampling(SamplingBase base) {
        samplingBase = base;
    }
}