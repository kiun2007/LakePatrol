package com.kssoft.lake.ui.activity.personnel;


import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.kssoft.lake.BR;
import com.kssoft.lake.R;
import com.kssoft.lake.data.model.CacheModel;
import com.kssoft.lake.data.model.XcTaskSt;
import com.kssoft.lake.data.types.SamplingType;
import com.kssoft.lake.databinding.ActivityLakeCalenderTaskBinding;
import com.kssoft.lake.net.requests.dto.TaskDto;
import com.kssoft.lake.net.requests.dto.XcRecdR;
import com.kssoft.lake.net.responses.vo.AreaStBprp;
import com.kssoft.lake.net.services.BaseService;
import com.kssoft.lake.net.services.ListService;
import com.kssoft.lake.net.services.RecordService;
import com.province.libcacheline.CacheSettings;
import com.province.libcacheline.CacheUploadEventer;
import com.province.libcacheline.data.JSONExtractor;
import com.province.libcacheline.data.UploadManager;
import com.province.libcacheline.data.beans.UploadObject;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.presenters.list.NetListProvider;
import kiun.com.bvroutine.utils.AlertUtil;
import kiun.com.bvroutine.utils.ListUtil;
import kiun.com.bvroutine.utils.MCString;
import kiun.com.bvroutine.utils.SharedUtil;
import kiun.com.bvroutine.views.dialog.MCDialogManager;
import kiun.com.bvroutine_apt.IntentValue;

/**
 * 文 件 名: LakeTask
 * 作 者: 刘春杰
 * 创建日期: 2020/7/28 16:22
 * 说明: 巡查任务
 */
public class LakeTaskActivity extends RequestBVActivity<ActivityLakeCalenderTaskBinding> {

    public static final Class clz = LakeTaskActivity.class;

    @IntentValue
    Integer index = 0;

    boolean isCache;

    @Override
    public int getViewId() {
        return R.layout.activity_lake_calender_task;
    }

    @Override
    public void initView() {
        listHandler.setEmptyDesc("当天没有相关任务");
        binding.setHandler(listHandler);
        setVariable(BR.taskDto, new TaskDto(index).listener(this::reload));
        listHandler.setTag(1, getResources().getDrawable(SamplingType.Lake.getIcon()));

        isCache = SharedUtil.getValue("isCache", false);
        getBarItem().setRightTitle(isCache ? "关闭" : "离线");

        getBarItem().getHandler().setRightCallBack((view)->{
            if (!isCache){
                AlertUtil.build(this, "是否开启离线填报功能, 并开始当前任务?\n(请在良好的网络状态下进行, 开启后可脱网使用)")
                        .setPositiveButton("确定", (v, d)->{

                            CacheModel model = new CacheModel();
                            model.setTitle("离线缓存");
                            MCDialogManager.create(this, R.layout.dialog_cache, model).transparent().setGravity(Gravity.CENTER).show();
                            rbp.addRequest(()->this.startCache(model),  this::onStartRecord);
                        }).setNegativeButton("取消", null).show();
            }else{

                AlertUtil.build(this, "是否关闭离线填报功能,并上传离线成果?\n(请在良好的网络状态下进行)")
                        .setPositiveButton("确定", (v, d)->{

                            CacheModel model = new CacheModel();
                            model.setTitle("上传缓存");

                            MCDialogManager dialog = MCDialogManager.create(this, R.layout.dialog_cache, model).transparent().setGravity(Gravity.CENTER).show();
                            rbp.addRequest(()->stopOffline(model),  v1 -> {
                                if (v1){
                                    dialog.dismiss();
                                    Toast.makeText(getContext(), "离线期间未进行任何操作!", Toast.LENGTH_LONG).show();
                                    SharedUtil.saveValue("isCache", isCache = false);
                                    getBarItem().setRightTitle("离线");
                                }
                            });
                        }).setNegativeButton("取消", null).show();
            }
        });
    }

    /**
     * 开始缓存当天任务.
     * @return
     * @throws Exception
     */
    private XcRecdR startCache(CacheModel cacheModel) throws Exception{

        XcRecdR xcRecdR = new XcRecdR();
        xcRecdR.setXctp(binding.getTaskDto().getXctp());
        xcRecdR.setRdst(0);

        cacheModel.progress(0, 1).setDesc("开启巡查");
        XcRecdR recdR = rbp.callServiceData(RecordService.class, s -> s.startRecord(xcRecdR));

        if (recdR == null){
            cacheModel.progress(1, 1).setDesc("开启失败");
            return null;
        }


        Map<String, Object> latLngMap = new HashMap<>();
        latLngMap.put("xctp", binding.getTaskDto().getXctp());
        latLngMap.put("tkcd", recdR.getTkcd());

        cacheModel.progress(0, 1).setDesc("获取任务站点");
        List<AreaStBprp> areaStBprpList = rbp.callServiceList(ListService.class, s -> s.getNearbySite(latLngMap), null);

        if (!ListUtil.isEmpty(areaStBprpList)){
            int index = 0;
            for (AreaStBprp areaStBprp : areaStBprpList){

                cacheModel.progress(index, areaStBprpList.size()).setDesc(String.format("缓存站点-%s", areaStBprp.getStnm()));
                rbp.callServiceList(BaseService.class, s -> s.getLakeLimit(null, areaStBprp.getStcd()), null);
                rbp.callServiceList(ListService.class, s -> s.xcTaskProList(areaStBprp.getStcd(), MCString.formatDate("yyyy-MM-dd", new Date()), xcRecdR.getRdcd()), null);
                index ++;
            }
        }

        cacheModel.progress(areaStBprpList.size(), areaStBprpList.size()).desc("已开启离线填报模式,填报将被缓存至本地,关闭离线后恢复正常!").setComplete(true);
        return recdR;
    }

    private boolean stopOffline(CacheModel cacheModel){

        List<UploadObject> uploadObjects = CacheSettings.readAllUpload();
        if (ListUtil.isEmpty(uploadObjects)){
            return true;
        }

        cacheModel.progress(0, uploadObjects.size()).setDesc("上传缓存数据");
        CacheSettings.startUpload(uploadObjects, new CacheUploadEventer() {
            @Override
            public boolean onUploadProgress(UploadObject uploadObject, int progress, int count, JSONExtractor extractor, UploadManager manager) {

                cacheModel.progress(progress, count);
                return true;
            }

            @Override
            public void onUploadStop(UploadObject uploadObject, boolean isUploadComplete, UploadManager manager) {
                if (isUploadComplete){
                    new Handler(getMainLooper()){
                        @Override
                        public void handleMessage(@NonNull Message msg) {
                            SharedUtil.saveValue("isCache", isCache = false);
                            getBarItem().setRightTitle("离线");
                            cacheModel.desc("上传完成, 离线已关闭, 填报数据恢复正常").setComplete(true);
                        }
                    }.sendEmptyMessage(0);
                }
            }

            @Override
            public void beforeUpload(UploadObject uploadObject, UploadManager manager) {

            }
        });
        return false;
    }

    public void onStartRecord(XcRecdR xcRecdR){

        binding.getTrailService().startRecord(xcRecdR);

        SharedUtil.saveValue("isCache", isCache = true);
        getBarItem().setRightTitle("关闭");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startPermission(()->{}, Manifest.permission.ACCESS_BACKGROUND_LOCATION);
        }
    }

    private void reload(TaskDto taskDto){

        SamplingType type = SamplingType.getType(taskDto.getXctp());
        if (type.getIcon() != -1){
            listHandler.setTag(1, getResources().getDrawable(type.getIcon()));
        }

        if (listHandler.getTag() instanceof NetListProvider){
            ((NetListProvider) listHandler.getTag()).refresh();
        }
    }

    ListHandler<XcTaskSt> listHandler = new ListHandler<XcTaskSt>(BR.handler, R.layout.list_error_normal){
        @Override
        public void onClick(Context context, int tag, XcTaskSt data) {
            if (!data.getState().equals("已巡")){
                Toast.makeText(context, "站点未巡查,没有巡查信息", Toast.LENGTH_LONG).show();
                return;
            }
            SamplingDetailsActivityHandler.openActivityNormal(context, data.getStcd(), data.getTkcd(), binding.getTaskDto().getXctp());
        }
    };

    public static void startFromType(Context context, String xctp){
        if (xctp != null){
            LakeTaskActivityHandler.openActivityNormal(context, Integer.parseInt(xctp));
        }
    }
}