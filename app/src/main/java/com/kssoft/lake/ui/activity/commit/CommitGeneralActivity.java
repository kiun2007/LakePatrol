package com.kssoft.lake.ui.activity.commit;

import com.kssoft.lake.R;
import com.kssoft.lake.data.SamplingBase;
import com.kssoft.lake.data.model.commit.SamplingGeneral;
import com.kssoft.lake.data.types.SamplingType;
import com.kssoft.lake.databinding.ActivityCommitObservationBinding;

import kiun.com.bvroutine.base.EventBean;
import kiun.com.bvroutine_apt.IntentValue;

public class CommitGeneralActivity extends CommitBaseActivity<ActivityCommitObservationBinding> {

    @IntentValue
    String recordId;

    @IntentValue
    String xctp;

    @Override
    public void initView() {
        super.initView();
        getBarItem().setTitle(SamplingType.getType(xctp).getTitle());
    }

    @Override
    protected SamplingBase getSampling() {
        if (recordId == null){
            return null;
        }
        SamplingGeneral general = new SamplingGeneral(recordId);
        return general;
    }

    @Override
    protected EventBean saveBean() {
        return binding.getData();
    }

    @Override
    protected String getXctp() {
        return xctp;
    }

    @Override
    protected void startSampling(SamplingBase base) {

    }

    @Override
    public int getViewId() {
        return R.layout.activity_commit_observation;
    }
}
