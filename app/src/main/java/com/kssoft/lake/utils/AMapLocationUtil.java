package com.kssoft.lake.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import kiun.com.bvroutine.BuildConfig;
import kiun.com.bvroutine.utils.AgileThread;

public class AMapLocationUtil {

    public static AMapLocationClient startLocation(Context context, AMapLocationListener setCaller){
        return startLocation(context, 1000*60*5, setCaller);
    }

    public static AMapLocationClient startLocation(Context context, int interval, AMapLocationListener setCaller, boolean isOnce){

        AMapLocationClient locationClient = new AMapLocationClient(context);
        AMapLocationClientOption option = new AMapLocationClientOption();

        if (BuildConfig.DEBUG){
            option.setMockEnable(true);
            option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        }else{
            option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        }
        option.setOnceLocation(isOnce);
        option.setInterval(interval);

        locationClient.setLocationOption(option);
        locationClient.setLocationListener(aMapLocation -> {

            if (context instanceof Activity){
                boolean isDead = ((Activity) context).isFinishing();
                if (isDead){
                    locationClient.stopLocation();
                    return;
                }
            }

            if (aMapLocation.getErrorCode() == 0){

                if (setCaller != null){

                    if (!aMapLocation.getAddress().isEmpty()){
                        setCaller.onLocationChanged(aMapLocation);
                        return;
                    }

                    new AgileThread(context, v -> {
                        v.into(()->setCaller.onLocationChanged(aMapLocation));
                    }).start();
                }
            }else{
                Log.e("Location", aMapLocation.getErrorInfo());
                Toast.makeText(context, BuildConfig.DEBUG ? aMapLocation.getErrorInfo() : "定位失败", Toast.LENGTH_LONG).show();
            }
        });
        locationClient.startLocation();
        return locationClient;
    }

    public static AMapLocationClient startLocation(Context context, int interval, AMapLocationListener setCaller){
        return startLocation(context, interval, setCaller, true);
    }
}
