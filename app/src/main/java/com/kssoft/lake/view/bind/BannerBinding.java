package com.kssoft.lake.view.bind;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.kssoft.lake.BuildConfig;
import com.kssoft.lake.data.BannerBean;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;

import java.util.List;

import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.interfaces.callers.FormViewCaller;
import kiun.com.bvroutine.utils.RetrofitUtil;
import retrofit2.Call;
import retrofit2.Response;

public class BannerBinding {
    public BannerBinding() {
    }

    @BindingAdapter("bannerNetCall")
    public static void setNetCall(Banner view, FormViewCaller caller) {
        if (view.getContext() instanceof RequestBVActivity) {

            RequestBVActivity activity = (RequestBVActivity) view.getContext();
            activity.addRequest(()-> RetrofitUtil.unpackWrap(null, ((Call<?>) caller.call(activity)).execute()), v -> {
                view.setAdapter(new GlideBannerImageAdapter((List<BannerBean>) v));
            });
        }
    }

    private static class GlideBannerImageAdapter extends BannerImageAdapter<BannerBean> {

        public GlideBannerImageAdapter(List<BannerBean> var1) {
            super(var1);
        }

        public void onBindView(BannerImageHolder var1, BannerBean var2, int var3, int var4) {
            StringBuilder var5 = new StringBuilder();
            var5.append(BuildConfig.Prefix);
            var5.append(var2.bannerUrl());
            String var6 = var5.toString();
            var1.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            var1.imageView.setBackgroundColor(-986896);
            Glide.with(var1.imageView.getContext()).load(var6).into(var1.imageView);
        }
    }
}