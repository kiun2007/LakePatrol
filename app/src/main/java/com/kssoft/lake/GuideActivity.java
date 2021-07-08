package com.kssoft.lake;

import android.content.Intent;

import androidx.loader.app.LoaderManager;

import com.kssoft.lake.databinding.ActivityGuideBinding;
import com.kssoft.lake.ui.activity.personnel.HomeActivity;
import com.kssoft.lake.ui.activity.LoginActivity;

import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.net.ServiceGenerator;
import kiun.com.bvroutine.utils.LocationUtils;

public class GuideActivity extends RequestBVActivity<ActivityGuideBinding> {

    @Override
    public int getViewId() {
        return R.layout.activity_guide;
    }

    @Override
    public void initView() {

        startPermission(LocationUtils.PERMISSION, ()->{
            if (ServiceGenerator.isLogin()){
                startActivity(new Intent(this, HomeActivity.clz));
            }else{
                startActivity(new Intent(this, LoginActivity.class));
            }
            finish();
        });
    }

    @Override
    public LoaderManager getSupportLoaderManager() {
        return null;
    }
}
