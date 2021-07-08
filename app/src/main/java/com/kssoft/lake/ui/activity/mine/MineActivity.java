package com.kssoft.lake.ui.activity.mine;

import com.kssoft.lake.R;
import com.kssoft.lake.base.presenter.imp.VersionUpdatePresenter;
import com.kssoft.lake.databinding.ActivityMineBinding;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.utils.AppUtil;

public class MineActivity extends RequestBVActivity<ActivityMineBinding> {
    public static final Class clz = MineActivity.class;

    public MineActivity() {
    }

    public int getViewId() {
        return R.layout.activity_mine;
    }

    public void initView() {
        ((ActivityMineBinding)this.binding).setVersionUpdate(new VersionUpdatePresenter(this));
        ((ActivityMineBinding)this.binding).setPackageInfo(AppUtil.getPackageInfo(this.getContext()));
    }
}
