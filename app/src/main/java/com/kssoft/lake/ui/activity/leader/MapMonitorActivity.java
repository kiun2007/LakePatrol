package com.kssoft.lake.ui.activity.leader;

import android.view.View;
import android.widget.Toast;

import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.kssoft.lake.BR;
import com.kssoft.lake.R;
import com.kssoft.lake.base.presenter.GraphicsEvent;
import com.kssoft.lake.data.SamplingBase;
import com.kssoft.lake.data.model.vo.MapStateVo;
import com.kssoft.lake.data.model.vo.MapStatistics;
import com.kssoft.lake.databinding.ActivityMapMonitorBinding;
import com.kssoft.lake.net.requests.dto.DataDto;
import com.kssoft.lake.net.requests.dto.MonitorDto;
import com.kssoft.lake.net.responses.vo.XcTaskPro;
import com.kssoft.lake.net.services.ListService;
import com.kssoft.lake.ui.activity.GisMapActivity;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.utils.ListUtil;
import kiun.com.bvroutine.utils.MapUtil;
import kiun.com.bvroutine.utils.ObjectUtil;
import kiun.com.bvroutine.views.dialog.MCDialogManager;
import kiun.com.bvroutine.views.popup.PopupWindowManager;

/**
 * 文 件 名: MapMonitor
 * 作 者: 刘春杰
 * 创建日期: 2020/8/21 15:27
 * 说明: 综合监视
 */
public class MapMonitorActivity extends GisMapActivity<ActivityMapMonitorBinding> implements GraphicsEvent {

    public static final Class clz = MapMonitorActivity.class;

    private String lastTm = null;

    private Map<String, MapStateVo> lastList = null;

    @Override
    public int getViewId() {
        return R.layout.activity_map_monitor;
    }

    @Override
    public void initView() {
        setVariable(BR.monitorDto, new MonitorDto().listener(this::onMonitorDtoChanged));
        onMonitorDtoChanged(binding.getMonitorDto());
    }

    private void onMonitorDtoChanged(MonitorDto monitorDto){

        if (!monitorDto.getTm().equals(lastTm)){
            lastList = null;
            addRequest(()-> getMapItems(monitorDto), this::setMapStateList);
        }else {
            setMapStateList(MapUtil.toList(lastList, (key, vo)->vo));
        }
    }

    private List<MapStateVo> getMapItems(MonitorDto monitorDto) throws Exception{
        rbp.callServiceList(ListService.class, s -> s.getOtherMapItem(monitorDto.getTm()), null);
        return rbp.callServiceList(ListService.class, s -> s.getMapTagging(monitorDto.getTm()), null);
    }

    private void setMapStateList(List<MapStateVo> list){

        if (lastList == null){
            lastList = new LinkedHashMap<>();
            ListUtil.map(list, v -> lastList.put(v.getStcd(), v));
        }

        List<MapStateVo> markerList = new LinkedList<>();

        MapStatistics mapStatistics = new MapStatistics();
        for (MapStateVo mapStateVo : list){
            if (mapStateVo.getMipMap() > 0 && binding.getMonitorDto().isChecked(mapStateVo.getXctp())){
                mapStatistics.add(mapStateVo.legendIndex());
                markerList.add(mapStateVo);
            }
        }

        binding.setMarkerList(markerList);
        binding.setStatistics(mapStatistics);
    }

    public void onMenuClick(View view){
        PopupWindowManager.create(view, R.layout.popup_menu_monitor_types, binding.getMonitorDto()).show();
    }

    private SamplingBase getSampling(String stcd, DataDto dataDto) throws Exception{
        List<XcTaskPro> list = rbp.callServiceList(ListService.class, s -> s.xcTaskProList(stcd, binding.getMonitorDto().getTm(), dataDto.getRdcd()), null);
        if (ListUtil.isEmpty(list)){
            return null;
        }
        SamplingBase samplingBase = ObjectUtil.newObject(dataDto.getType());
        samplingBase.setSourceCopy(list, true);
        return samplingBase;
    }

    @Override
    public void onGraphicsClick(Graphic graphic) {

        String key = (String) graphic.getAttributes().get("key");

        MapStateVo stateVo = lastList == null ? null : lastList.get(key);

        if (stateVo != null){
            if (!"1".equals(stateVo.getState())){
                Toast.makeText(this, "站点未采样", Toast.LENGTH_LONG).show();
                return;
            }
            DataDto dataDto = new DataDto(stateVo.getStcd(), stateVo.getXctp(), stateVo.getTkcd());
            addRequest(()-> getSampling(stateVo.getStcd(), dataDto), this::setData);
        }
    }

    @Override
    public void onMapAction(Envelope extent, int level) {

    }

    private void setData(SamplingBase base){

        if (base != null){
            MCDialogManager manager = MCDialogManager.create(getContext(), R.layout.dialog_marker_information, base, BR.data, BR.dialog)
                           .transparent().setCancelable(true).show();
            manager.setOnCancelListener((dialog)->{
            });
        }else{
            Toast.makeText(this, "未查询到采样数据", Toast.LENGTH_LONG).show();
        }
    }
}