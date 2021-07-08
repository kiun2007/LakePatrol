package com.kssoft.lake.view.value;

import android.widget.ImageView;

import com.kssoft.lake.data.BannerBean;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;

import java.util.List;

import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.base.binding.value.BindConvert;
import kiun.com.bvroutine.interfaces.callers.FormViewCaller;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;
import kiun.com.bvroutine.interfaces.wrap.ListWrap;
import retrofit2.Call;

public class NetBannerBindConvert extends BindConvert<Banner, FormViewCaller, Object> {

    private class GlideBannerImageAdapter extends BannerImageAdapter<BannerBean>{

        public GlideBannerImageAdapter(List<BannerBean> mData) {
            super(mData);
        }

        @Override
        public void onBindView(BannerImageHolder holder, BannerBean data, int position, int size) {
            holder.imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }

    }

    private FormViewCaller caller;
    private RequestBindingPresenter rbp;

    public NetBannerBindConvert(Banner view) {
        super(view);
        if(view.getContext() instanceof RequestBVActivity){
            RequestBVActivity activity = (RequestBVActivity) view.getContext();
            rbp = activity.getRequestPresenter();
        }
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public void setValue(FormViewCaller value) {
        caller = value;
        rbp.addRequest(this::getBannerBeans, v->{
            view.setAdapter(new GlideBannerImageAdapter(v));
        });
    }

    private List<BannerBean> getBannerBeans() throws Exception {

        Call call = (Call) caller.call(null);
        ListWrap<BannerBean> listWrap = rbp.executeList(call);
        if (!listWrap.isSuccess()){
            throw new Exception("网络错误");
        }
        return listWrap.wrapList();
    }
}
