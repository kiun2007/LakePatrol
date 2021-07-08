package com.kssoft.lake.ui.activity.personnel;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.esri.arcgisruntime.geometry.Point;
import com.kssoft.lake.BR;
import com.kssoft.lake.R;
import com.kssoft.lake.data.model.CacheModel;
import com.kssoft.lake.data.types.RecordState;
import com.kssoft.lake.data.types.SamplingType;
import com.kssoft.lake.databinding.ActivityMapLakeInspectionBinding;
import com.kssoft.lake.net.requests.dto.XcRecdR;
import com.kssoft.lake.services.TrailService;
import com.kssoft.lake.ui.activity.GisMapActivity;
import com.kssoft.lake.ui.activity.commit.CommitHydrologyActivityHandler;
import com.kssoft.lake.ui.activity.commit.CommitLakeActivityHandler;
import com.kssoft.lake.ui.activity.commit.CommitManualActivityHandler;
import com.kssoft.lake.ui.activity.commit.CommitUrgentActivityHandler;
import kiun.com.bvroutine.ActivityCallback;
import kiun.com.bvroutine.utils.AlertUtil;
import kiun.com.bvroutine.utils.SharedUtil;
import kiun.com.bvroutine.views.dialog.MCDialogManager;
import kiun.com.bvroutine_apt.IntentValue;

/**
 * 文 件 名: LakeInspectionMap
 * 作 者: 刘春杰
 * 创建日期: 2020/7/31 15:30
 * 说明: 巡查地图
 */
public class LakeInspectionGisMapActivity extends GisMapActivity<ActivityMapLakeInspectionBinding> {

    public static final Class clz = LakeInspectionGisMapActivity.class;

    //巡测类型 0湖泛，1水文，2人工，3应急.
    @IntentValue
    String xctp;

    @Override
    public int getViewId() {
        return R.layout.activity_map_lake_inspection;
    }

    @Override
    public void initView() {
        binding.setState(RecordState.Stopped);

        XcRecdR xcRecdR = new XcRecdR();
        xcRecdR.setXctp(xctp);
        xcRecdR.setRdst(0);
        setVariable(BR.record, xcRecdR);

        String[] titles = new String[]{"湖泛巡查","水文巡查","人工观测","应急监测"};
        getBarItem().setTitle(titles[Integer.parseInt(xctp)]);

        boolean isNoMap = SharedUtil.getValue("isNoMap", false);
        getBarItem().setRightTitle(isNoMap ? "启用" : "禁用" );
        getIntent().putExtra("NoMap", isNoMap);
        binding.setNoMap(isNoMap);

        getBarItem().getHandler().setRightCallBack((view)->{
            AlertUtil.build(this, isNoMap ? "是否启用地图功能,需重启地图界面" : "是否禁用地图功能,需重启地图界面")
                    .setPositiveButton("确定", (v, d)->{
                        SharedUtil.saveValue("isNoMap", !isNoMap);
                        restart();
                    }).setNegativeButton("取消", null).show();
        });
    }

    private void startRecord(){
        binding.setState(RecordState.Starting);
    }

    public void onReportClick(View view){

        if (binding.getTrailService() == null && binding.getTrailService().isRecording()){
            return;
        }

        String recordId = binding.getTrailService().getRecord().getRdcd();
        if ("0".equals(xctp)){
            CommitLakeActivityHandler.openActivityNormal(this, recordId);
        }else if ("1".equals(xctp)){
            CommitHydrologyActivityHandler.openActivityNormal(this, recordId);
        }else if ("2".equals(xctp)){
            CommitManualActivityHandler.openActivityNormal(this, recordId);
        }else if ("3".equals(xctp)){
            CommitUrgentActivityHandler.openActivityNormal(this, recordId);
        }
    }

    public void onStart(XcRecdR xcRecdR){

        startRecord();
        binding.setRecord(xcRecdR);
        binding.getTrailService().startRecord(binding.getRecord());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startPermission(()->{}, Manifest.permission.ACCESS_BACKGROUND_LOCATION);
        }

        if ("3".equals(xctp)){
            AlertUtil.build(this, "是否启用计划外填报模式?启用后不再填报计划内的站点.")
                    .setNegativeButton("否", (dialog, which) ->{
                        SharedUtil.saveValue("isPlan", false);
                    }).setPositiveButton("是", (dialog, which) -> {
                        SharedUtil.saveValue("isPlan", true);
                    }).setCancelable(false).show();
        }
    }

    public void onStop(Integer v){

        if (binding.getTrailService() != null){
            binding.getTrailService().stopRecord();
        }
        binding.setState(RecordState.Stopped);
        Toast.makeText(this,"结束巡查成功!", Toast.LENGTH_LONG).show();

        if (xctp != null){
            SharedUtil.delete(SamplingType.getType(xctp).getClz().getName());
        }
        finish();
    }

    public void onPause(Integer v){
        if (binding.getTrailService() != null){
            binding.getTrailService().pauseRecord();
        }
        Toast.makeText(this,"已暂停当前巡查任务!", Toast.LENGTH_LONG).show();
        finish();
    }

    public void onResume(Integer v){
        if (binding.getTrailService() != null){
            binding.getTrailService().resumeRecord();
        }
        binding.setState(RecordState.Starting);
        Toast.makeText(this,"已恢复当前巡查任务!", Toast.LENGTH_LONG).show();
    }

    public static Intent create(Context context, String xctp){
        return LakeInspectionGisMapActivityHandler.openActivityIntent(context, xctp).putExtra(ActivityCallback.IS_REMOVE, false);
    }

    @Override
    public void onImportComplete(Object value) {

        if (value instanceof TrailService){
            TrailService service = (TrailService) value;
            if (service.isRecording(xctp)){
                setVariable(BR.record, service.getRecord());
                xctp = binding.getRecord().getXctp();
                startRecord();
            }else if (service.isPaused(xctp)){
                setVariable(BR.record, service.getRecord());
                binding.setState(RecordState.Paused);
            }
        }
    }
}