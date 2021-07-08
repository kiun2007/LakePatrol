package com.kssoft.lake.data;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import androidx.databinding.ViewDataBinding;
import com.kssoft.lake.services.TrailService;
import java.lang.reflect.Method;
import kiun.com.bvroutine.base.ServiceBinder;
import kiun.com.bvroutine.base.binding.variable.VariableBinding;

public abstract class LocationVariableSet<V extends LocationBean> extends VariableBinding<V> implements ServiceConnection {

    protected V locationBean;

    public LocationVariableSet(Context context, ViewDataBinding viewDataBinding, Class<V> clz, Method method) {
        super(context, viewDataBinding, clz, method);
    }


    @Override
    public void start() {
        context.bindService(new Intent(context, TrailService.class), this, Context.BIND_AUTO_CREATE);
        try {
            locationBean = clz.newInstance();
            setVariable(locationBean);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void end() {
        context.unbindService(this);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        if (service instanceof ServiceBinder){
            TrailService trailService = (TrailService) ((ServiceBinder) service).getService();
            trailService.setLocationBean(locationBean);
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
    }
}
