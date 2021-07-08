package com.kssoft.lake.ui.activity.commit;

import com.kssoft.lake.R;
import com.kssoft.lake.data.SamplingBase;
import com.kssoft.lake.data.model.commit.XcLakeR;
import com.kssoft.lake.databinding.ActivityCommitLakeFloodingBinding;
import com.kssoft.lake.net.responses.vo.XcLkwqB;
import com.kssoft.lake.net.services.BaseService;
import java.util.List;
import kiun.com.bvroutine.base.EventBean;
import kiun.com.bvroutine.utils.ListUtil;
import kiun.com.bvroutine_apt.ActivityOpen;
import kiun.com.bvroutine_apt.IntentValue;
import static kiun.com.bvroutine.base.jexl.RuntimeContext.*;

/**
 * 文 件 名: CommitLake
 * 作 者: 刘春杰
 * 创建日期: 2020/8/5 23:22
 * 说明: 湖泛巡查填报
 */
public class CommitLakeActivity extends CommitBaseActivity<ActivityCommitLakeFloodingBinding>{

    public static final Class clz = CommitLakeActivity.class;

    @IntentValue
    String recordId;

    @Override
    public int getViewId() {
        return R.layout.activity_commit_lake_flooding;
    }

    @Override
    protected void onSamplingChanged(SamplingBase samplingBase) {
        super.onSamplingChanged(samplingBase);
        addRequest(()-> rbp.callServiceList(BaseService.class,s -> s.getLakeLimit(null, samplingBase.getStcd()), null), this::limitComplete);
    }

    private void limitComplete(List<XcLkwqB> limit){
        if (!ListUtil.isEmpty(limit)){

            runTime().set("lakeLimit", limit.get(0));
        }
    }

    @Override
    protected SamplingBase getSampling() {
        if (recordId == null){
            return null;
        }
        return new XcLakeR(recordId);
    }

    @Override
    protected EventBean saveBean() {
        return binding.getData();
    }

    @Override
    protected String getXctp() {
        return "0";
    }

    @Override
    @ActivityOpen
    protected void startSampling(SamplingBase base) {
        samplingBase = base;
    }
}