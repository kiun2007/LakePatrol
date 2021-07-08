package com.kssoft.lake.ui.activity;

import android.location.Location;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.mapping.view.MapView;

import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.base.binding.variable.AutoImportHandler;
import kiun.com.bvroutine.utils.LocationUtils;

public abstract class GisMapActivity<T extends ViewDataBinding> extends RequestBVActivity<T> implements AutoImportHandler<Object> {

    protected MapView mapView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startPermission(LocationUtils.PERMISSION, this::startLocation);
    }

    @Override
    public void onImportComplete(Object value) {
    }

    protected void startLocation(){
        Location location = LocationUtils.getLocation(this);
        if (location != null){//
            mapView.setViewpointCenterAsync(new Point(location.getLongitude(), location.getLatitude()), 150);
        }
    }
}
