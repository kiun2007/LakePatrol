package com.kssoft.lake.ui.activity.commit;

import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

import com.kssoft.lake.BR;
import com.kssoft.lake.data.LastLocation;
import com.kssoft.lake.data.SamplingBase;
import com.kssoft.lake.net.responses.vo.AreaStBprp;
import com.kssoft.lake.net.services.ListService;
import com.kssoft.lake.services.TrailService;
import com.kssoft.lake.view.value.EditTextLimitBindConvert;

import java.util.Date;
import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.base.EventBean;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.base.binding.variable.AutoImportHandler;
import kiun.com.bvroutine.utils.ContextUtil;
import kiun.com.bvroutine.utils.ListUtil;
import kiun.com.bvroutine.utils.MCString;
import kiun.com.bvroutine.utils.ObjectUtil;
import kiun.com.bvroutine.utils.SharedUtil;

/**
 * 文 件 名: CommitBaseActivity
 * 作 者: 刘春杰
 * 创建日期: 2020/8/5 22:07
 * 说明: 基础填报
 */
public abstract class CommitBaseActivity<T extends ViewDataBinding> extends RequestBVActivity<T> implements AutoImportHandler<Object> {

    public static final Class clz = CommitBaseActivity.class;

    protected SamplingBase samplingBase;

    private SamplingBase saveValue;

    protected boolean isNoPlan = false;

    protected abstract SamplingBase getSampling();

    protected abstract EventBean saveBean();

    protected abstract String getXctp();

    protected void setSiteList(List list){

        setVariable(BR.siteList, list);
        if (!ListUtil.isEmpty(list) && list.get(0) instanceof AreaStBprp){

            AreaStBprp stBprp = (AreaStBprp) list.get(0);
            saveValue.setStcd(stBprp.getStcd());
            saveValue.setStnm(stBprp.getStnm());
            saveValue.onChanged();
        }
    }

    protected abstract void startSampling(SamplingBase base);

    @Override
    public void initView() {
        binding.setVariable(BR.editMode, samplingBase != null);
        SamplingBase samplingBase = getSampling();

        if (ContextUtil.getBoolean(this, "isCheck")){
            getIntent().putExtra(EditTextLimitBindConvert.LimitCount, 3);
        }

        if (samplingBase != null){
            setVariable(BR.data, getSampling());
        }else{
            this.samplingBase = (SamplingBase) getIntent().getSerializableExtra("sampling");

            if (this.samplingBase != null){
                String stcd = this.samplingBase.getStcd();
                String tm = MCString.formatDate("yyyy-MM-dd", this.samplingBase.samplingTime());
                String rdcd = this.samplingBase.getRdcd();
                String stnm = this.samplingBase.getStnm();

                this.samplingBase = ObjectUtil.newObject(this.samplingBase.getClass());
                this.samplingBase.setRdcd(rdcd);
                this.samplingBase.setStnm(stnm);
                this.samplingBase.setStcd(stcd);
                this.samplingBase.setCheck(true);

                rbp.addRequest(()-> rbp.callServiceList(ListService.class, s -> s.xcTaskProList(stcd, tm, rdcd), null), this.samplingBase::setSource);
                super.setVariable(BR.data, this.samplingBase);
            }
        }
    }

    @Override
    public void onImportComplete(Object value) {
        if (value instanceof TrailService){
            TrailService trailService = (TrailService) value;

            if (samplingBase == null){
                if (trailService.getRecord() != null) {
                    searchNearSite(trailService.getLastLocation(), trailService.getRecord().getTkcd());
                }else{
                    Toast.makeText(this, "无法获取到任务,无法完成填报", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    protected void searchNearSite(LastLocation lastLocation, String tkcd){
        Map<String, Object> latLngMap = lastLocation.toMap("lttd","lgtd");
        latLngMap.put("xctp", getXctp());
        latLngMap.put("tkcd", (tkcd == null || isNoPlan) ? "" : tkcd);
        if (isNoPlan == false) {
            addRequest(()->rbp.callServiceList(ListService.class, s -> s.getNearbySite(latLngMap), null), this::setSiteList);
        }
    }

    protected void onSamplingChanged(SamplingBase samplingBase){
        rbp.addRequest(()-> rbp.callServiceList(ListService.class, s -> s.xcTaskProList(samplingBase.getStcd(), MCString.formatDate("yyyy-MM-dd", new Date()),samplingBase.getRdcd()), null), samplingBase::setSource);
    }

    @Override
    protected void setVariable(int variableId, @Nullable Object value) {

        if (value instanceof SamplingBase){
            saveValue = (SamplingBase) (value = SharedUtil.getValue(value.getClass().getName(), value));
            saveValue.listener(this::onSamplingChanged);
        }
        super.setVariable(variableId, value);
    }

    @Override
    public void finish() {

        if (samplingBase == null){
            EventBean eventBean = saveBean();
            if (eventBean != null){
                SharedUtil.saveValue(eventBean.getClass().getName(), eventBean);
                Toast.makeText(this, "将自动为您保存未提交的数据", Toast.LENGTH_LONG).show();
            }
        }
        setResult(RESULT_OK);
        super.finish();
    }

    public void onComplete(Integer id){

        if (saveValue != null){
            SharedUtil.delete(saveValue.getClass().getName());
        }
        Toast.makeText(this, "提交成功", Toast.LENGTH_LONG).show();
        setResult(RESULT_OK);
        super.finish();
    }
}