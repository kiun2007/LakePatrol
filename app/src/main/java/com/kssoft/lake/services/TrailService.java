package com.kssoft.lake.services;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.ObservableField;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.kssoft.lake.MainApplication;
import com.kssoft.lake.data.LastLocation;
import com.kssoft.lake.data.LocationBean;
import com.kssoft.lake.data.types.RecordState;
import com.kssoft.lake.net.requests.dto.RecdDto;
import com.kssoft.lake.net.requests.dto.XcRecdR;
import com.kssoft.lake.net.requests.dto.XcTrailR;
import com.kssoft.lake.net.services.RecordService;
import com.kssoft.lake.utils.AMapLocationUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.base.BaseService;
import kiun.com.bvroutine.base.binding.variable.AutoImport;
import kiun.com.bvroutine.base.binding.variable.ServiceVariableSet;
import kiun.com.bvroutine.interfaces.Scheduled;
import kiun.com.bvroutine.net.ServiceGenerator;
import kiun.com.bvroutine.service.LocationService;
import kiun.com.bvroutine.utils.AgileThread;
import kiun.com.bvroutine.utils.LocationUtils;
import kiun.com.bvroutine.utils.RetrofitUtil;
import kiun.com.bvroutine.utils.SharedUtil;

@AutoImport(ServiceVariableSet.class)
public class TrailService extends BaseService implements AMapLocationListener {

    private static final String RecordSaveTag = "record";

    private LastLocation lastLocation;
    private XcRecdR xcRecdR;
    private LocationBean locationBean;
    AgileThread agileThread;

    private ObservableField<List<XcTrailR>> pointsField = new ObservableField<>();
    private Map<String, ObservableField<Boolean>> typeAndObservable = new HashMap<>();


    @Override
    public void onCreate() {
        super.onCreate();
        AMapLocationUtil.startLocation(getBaseContext(), 1000 * 10,this, false);
    }

    public TrailService(){

        lastLocation = getShamLocation();

        this.xcRecdR = SharedUtil.getValue(RecordSaveTag, new XcRecdR());
        if (this.xcRecdR != null && !TextUtils.isEmpty(this.xcRecdR.getRdcd())){
            lastLocation = SharedUtil.getValue("lastLocation", new LastLocation(-1,-1, null));
            startRecord(this.xcRecdR);
        }else{
            this.xcRecdR = null;
        }
    }

    public void putTypeAndObservable(String type, ObservableField<Boolean> observableField){
        typeAndObservable.put(type, observableField);
        changeVisible();
    }

    private void changeVisible(){

        if (xcRecdR != null){
            for (ObservableField<Boolean> field : typeAndObservable.values()){
                field.set(false);
            }

            ObservableField<Boolean> field = typeAndObservable.get(xcRecdR.getXctp());
            if (field != null){
                field.set(true);
            }
        }else{
            for (ObservableField<Boolean> field : typeAndObservable.values()){
                field.set(true);
            }
        }
    }

    public XcRecdR getRecord() {
        return xcRecdR;
    }

    public boolean isRecording(){
        return agileThread != null;
    }

    public boolean isRecording(String type) {
        return xcRecdR != null && type.equals(xcRecdR.getXctp()) && xcRecdR.getState() == RecordState.Starting;
    }

    public boolean isPaused(String type){
        return xcRecdR != null && type.equals(xcRecdR.getXctp()) && xcRecdR.getState() == RecordState.Paused;
    }

    public void startRecord(XcRecdR xcRecdR){

        this.xcRecdR = xcRecdR;

        if (this.xcRecdR != null){
            SharedUtil.saveValue(RecordSaveTag, xcRecdR);
            changeVisible();
        }
        xcRecdR.setEdtm(new Date());

        agileThread = new AgileThread(MainApplication.getApplication(), (thread)->{

            RecdDto recdDto = new RecdDto();
            recdDto.setRdcd(xcRecdR.getRdcd());
            List<XcTrailR> xcTrailRList = RetrofitUtil.callServiceList(RecordService.class, s -> s.getTrailList(recdDto));
            pointsField.set(xcTrailRList);

            while (this.xcRecdR != null && !thread.isKilling()){

                //坐标如果位移不超过0.1米, 5分钟传上一次坐标.
                XcTrailR trailR = thread.intoWait(false, null);

                if (trailR == null && lastLocation != null){
                    trailR = createTrail(lastLocation);
                }

                try {
                    XcTrailR finalTrailR = trailR;
                    if (finalTrailR != null && xcRecdR.getRdst() == 0){
                        RetrofitUtil.callServiceData(RecordService.class, s->s.addTrail(finalTrailR), ServiceGenerator::createService);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            agileThread = null;
        });
        agileThread.setMaxWait(5 * 60 * 1000).start();
    }

    /**
     * 暂停
     */
    public void pauseRecord(){
        xcRecdR.setRdst(1);
        SharedUtil.saveValue(RecordSaveTag, xcRecdR);
    }

    /**
     * 继续
     */
    public void resumeRecord(){
        xcRecdR.setRdst(0);
        SharedUtil.saveValue(RecordSaveTag, xcRecdR);
    }

    /**
     * 停止
     */
    public void stopRecord(){
        xcRecdR = null;
        SharedUtil.delete(RecordSaveTag);
        pointsField.set(null);
        changeVisible();
    }

    @Override
    public void onDestroy() {
        if (agileThread != null){
            agileThread.kill();
        }

        SharedUtil.delete(RecordSaveTag);
        super.onDestroy();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    public LastLocation getLastLocation() {
        return lastLocation;
    }

    public LastLocation getShamLocation(){
        return new LastLocation(31.388850,120.096590, null);
    }

    public ObservableField<List<XcTrailR>> getPointsField() {
        return pointsField;
    }

    private XcTrailR createTrail(LastLocation lastLocation){

        XcTrailR trailR = new XcTrailR();
        trailR.setLttd(lastLocation.getLat());
        trailR.setLgtd(lastLocation.getLng());
        trailR.setAddvnm(lastLocation.getAddress());
        trailR.setTm(new Date());
        trailR.setRdcd(xcRecdR.getRdcd());
        return trailR;
    }

    public void setLocationBean(LocationBean locationBean) {
        this.locationBean = locationBean;
        if (lastLocation != null){
            locationBean.latitude(lastLocation.getLat());
            locationBean.longitude(lastLocation.getLng());
        }
    }

    public void onLocation(AMapLocation location){

        if (location == null){
            Log.i("GPS", "获取位置信息失败!");
            return;
        }

        if (locationBean != null){
            locationBean.latitude(location.getLatitude());
            locationBean.longitude(location.getLongitude());
        }

        String address = location.getAddress();
        LastLocation nowLocation = new LastLocation(location.getLatitude(), location.getLongitude(), address).convert84();

        if (lastLocation != null){
            if (agileThread != null && xcRecdR != null){
                if (lastLocation.getLat() > 0){

                    float mileage = nowLocation.toLocation().distanceTo(lastLocation.toLocation());
                    if (mileage < 0.1){
                        return;
                    }
                    xcRecdR.addMileage((double) mileage);
                }

                SharedUtil.saveValue("lastLocation", lastLocation);
                SharedUtil.saveValue(RecordSaveTag, xcRecdR);
                XcTrailR xcTrailR = createTrail(nowLocation);
                if (pointsField.get() != null){
                    pointsField.get().add(xcTrailR);
                    pointsField.notifyChange();
                }
                xcRecdR.setEdtm(new Date());
                agileThread.notify(xcTrailR);
            }
        }
        lastLocation = nowLocation;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        onLocation(aMapLocation);
    }
}
