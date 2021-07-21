package com.kssoft.lake.ui.activity.list;

import android.content.Context;

import com.kssoft.lake.BR;
import com.kssoft.lake.BuildConfig;
import com.kssoft.lake.R;
import com.kssoft.lake.databinding.ActivityListRecordBinding;
import com.kssoft.lake.net.requests.dto.RecdDto;
import com.kssoft.lake.net.requests.dto.XcRecdR;
import com.kssoft.lake.ui.activity.personnel.MapTrailActivityHandler;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.presenters.list.NetListProvider;

public class ListRecordActivity extends RequestBVActivity<ActivityListRecordBinding> {
    public static final Class clz = ListRecordActivity.class;

    ListHandler<XcRecdR> listHandler = new ListHandler<XcRecdR>(BR.handler, R.layout.list_error_normal) {
        public void onClick(Context var1, int var2, XcRecdR var3) {
            if (var2 == 1) {
                ListSamplingActivityHandler.openActivityNormal(ListRecordActivity.this.getContext(), var3.getRdcd(), var3.getXctp());
            } else {
                if (var2 == 2) {
                    MapTrailActivityHandler.openActivityNormal(var1, var3.getRdcd());
                }
            }
        }
    };
    String type;

    private void onRecordDtoChanged(RecdDto var1) {
        if (this.listHandler.getTag() instanceof NetListProvider) {
            ((NetListProvider)this.listHandler.getTag()).refresh();
        }
    }

    public Context getContext() {
        return this;
    }

    public int getViewId() {
        return R.layout.activity_list_record;
    }

    public void initView() {
        this.setVariable(BR.recordDto, new RecdDto("0").listener(this::onRecordDtoChanged));
        this.binding.setHandler(this.listHandler);
//        this.getBarItem().getHandler().setRightCallBack(new EJE0sL2bGdHQVDuGnCn_aQThxYY(this));
    }
}