package com.kssoft.lake.data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.kssoft.lake.base.EditTextCheck;
import com.kssoft.lake.data.model.XcSnimdtF;
import com.kssoft.lake.data.model.XcTaskP;
import com.kssoft.lake.data.types.SamplingType;
import com.kssoft.lake.net.responses.vo.XcTaskPro;
import com.kssoft.lake.ui.activity.commit.CommitBaseActivity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.base.EventBean;
import kiun.com.bvroutine.data.verify.NotNull;
import kiun.com.bvroutine.interfaces.GeneralItemTextListener;
import kiun.com.bvroutine.interfaces.verify.Verify;
import kiun.com.bvroutine.utils.ListUtil;
import kiun.com.bvroutine.utils.ObjectUtil;
import kiun.com.bvroutine.views.text.GeneralItemText;

/**
 * 采样通用.
 */
public abstract class SamplingBase extends EventBean implements Serializable, GeneralItemTextListener {

    public abstract Class<? extends CommitBaseActivity> commitActivity();

    /**
     * 子类决定加载哪个视图，不同的数据对应不同的界面.
     * @return
     */
    public abstract int viewLayoutId();

    /**
     * 在校核列表中的标题
     * @return
     */
    public abstract String itemTitle();

    /**
     * 在校核列表中的时间格式化.
     * @param format 格式化字符.
     * @return 时间格式化之后的数据.
     */
    public abstract String itemTime(String format);

    /**
     * 样本采集时间.
     * @return
     */
    public abstract Date samplingTime();

    public abstract String type();

    /**
     * 采样站点名称.
     */
    private String stnm;

    /**
     * 行政区域.
     */
    private String addvnm;

    private String isLakeType = "0";

    /**
     * 展开数据.
     * @param expansion true展开, false 收起.
     */
    @JSONField(serialize = false)
    private boolean expansion = false;

    //测站代码
    private String stcd;
    //巡测记录代码
    private String rdcd;
    //备注情况
    private String remark;

    //报送类型 0正常，1临时
    private String sbtp;

    //上报人代码
    private String sbp;

    private String ckstate;

    //上报人姓名
    private String sbpnm;

    //上报时间
//    private String spt;

    //校核人名称
    private String ckpnm;

    //校核时间
    private String cktm;

    //对应上报的图片数据集
    @Verify(value = NotNull.class, desc = "至少上传一张图片", pass = "!classChecker.isClass(that,'XcLakeR')||that.isCheck()")
    private List<XcSnimdtF> xcsnimdtf;

    //对应上报时校核人员数据集
    private List<XcTaskP> xcTaskP;

    @JSONField(serialize = false)
    private List<XcTaskPro> source = new LinkedList<>();

    @JSONField(serialize = false)
//    @Verify(value = ItemCheck.class, desc = "红标项必须填写", extra = "item.isPass()")
    @Verify(value = EditTextCheck.class, desc = "至少填写一项", extra = "item.isPass()")
    private List<XcTaskPro> other = new LinkedList<>();

    private boolean isCheck = false;

    public SamplingBase(String rdcd) {
        this();
        this.rdcd = rdcd;
    }

    public SamplingBase(){
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setSource(List<XcTaskPro> source) {
        setSourceCopy(source, false);
    }

    public void setSourceCopy(List<XcTaskPro> source, boolean isCopy) {
        this.source = source;
        other.clear();

        Map<String, XcTaskPro> map = new HashMap<>();
        ListUtil.map(source, v -> map.put(v.getEnname().toLowerCase(), v));

        stcd = source.isEmpty() ? "" : source.get(0).getStcd();

        JSONObject samplingMap = (JSONObject) JSON.toJSON(this);
        List<String> names = new LinkedList<>();
        for (String name : samplingMap.keySet()){
            names.add(name.toLowerCase());

            if (isCopy){
                XcTaskPro pro = map.get(name.toLowerCase());
                if (pro != null && pro.getSbv() != null){
                    samplingMap.put(name, pro.getSbv());
                }
            }
        }

        if (isCopy){
            SamplingBase copy = JSON.parseObject(JSON.toJSONString(samplingMap), this.getClass());
            ObjectUtil.copyByMapping(this, copy);
        }

        other = ListUtil.filter(source, item -> {
            if (isCheck){
                item.setJhv(item.getSbv());
            }
            if (names.indexOf(item.getEnname().toLowerCase()) < 0){
                item.setSbv(null);
                return true;
            }
            return false;
        });

        onChanged();
    }

    public String getStcd() {
        return stcd;
    }

    public void setStcd(String stcd) {
        this.stcd = stcd;
    }

    public String getRdcd() {
        return rdcd;
    }

    public void setRdcd(String rdcd) {
        this.rdcd = rdcd;
    }

    public String getIsLakeType() {
        return isLakeType;
    }

    public void setIsLakeType(String var1) {
        this.isLakeType = var1;
        this.onChanged();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSbtp() {
        return sbtp;
    }

    public void setSbtp(String sbtp) {
        this.sbtp = sbtp;
        onChanged();
    }

    public String getSbp() {
        return sbp;
    }

    public void setSbp(String sbp) {
        this.sbp = sbp;
    }

    public List<XcSnimdtF> getXcsnimdtf() {
        return xcsnimdtf;
    }

    public void setXcsnimdtf(List<XcSnimdtF> xcsnimdtf) {
        this.xcsnimdtf = xcsnimdtf;
    }

    public List<XcTaskP> getXcTaskP() {
        return xcTaskP;
    }

    public void setXcTaskP(List<XcTaskP> xcTaskP) {
        this.xcTaskP = xcTaskP;
    }

    public String getSbpnm() {
        return sbpnm;
    }

    public void setSbpnm(String sbpnm) {
        this.sbpnm = sbpnm;
    }

//    public String getSpt() {
//        return spt;
//    }
//
//    public void setSpt(String spt) {
//        this.spt = spt;
//    }

    public String getCkpnm() {
        return ckpnm;
    }

    public void setCkpnm(String ckpnm) {
        this.ckpnm = ckpnm;
    }

    public String getCktm() {
        return cktm;
    }

    public void setCktm(String cktm) {
        this.cktm = cktm;
    }

    public String getStnm() {
        return stnm;
    }

    public void setStnm(String stnm) {
        this.stnm = stnm;
        onChanged(false);
    }

    @JSONField(serialize = false)
    public String getNames(){
        if (!ListUtil.isEmpty(xcTaskP)){
            List<String> names = ListUtil.toList(xcTaskP, item->item.getPnm());
            return ListUtil.join(names, ",");
        }
        return "未分配人员";
    }

    public String getAddvnm() {
        return addvnm;
    }

    public void setAddvnm(String addvnm) {
        this.addvnm = addvnm;
    }

    public boolean isExpansion() {
        return expansion;
    }

    public void setExpansion(boolean expansion) {
        this.expansion = expansion;
        onChanged();
    }

    protected String formatItemTitle(String type){
        return String.format("%s%s", itemTime("yyyy年M月d日"), type);
    }

    @Override
    public void onChanged(GeneralItemText view, String id, String title, Object extra) {
        stcd = id;
        setStnm(title);
    }

    @JSONField(serialize = false)
    public List<XcTaskPro> toProList(){

        JSONObject map = JSON.parseObject(JSON.toJSONString(this));

        for (XcTaskPro taskPro : source){
            Object value = map.get(taskPro.getEnname().toLowerCase());
            if (value != null){
                if (isCheck){
                    taskPro.setJhv(String.valueOf(value));
                }else{
                    taskPro.setSbv(String.valueOf(value));
                }
                taskPro.setSttp(SamplingType.getType(getClass()).getType());
            }

            taskPro.setStnm(stnm);
            taskPro.setRdcd(rdcd);
            taskPro.setState(isCheck ? "6" : "5");
        }

        if(ListUtil.filter(source, item -> item.getEnname().equals("XCSNIMDTF")).isEmpty() && !ListUtil.isEmpty(xcsnimdtf)){

            XcTaskPro taskPro = new XcTaskPro();

            String value = JSON.toJSONString(xcsnimdtf);
            taskPro.setChname("图片");
            taskPro.setEnname("XCSNIMDTF");
            taskPro.setRdcd(rdcd);
            taskPro.setStnm(stnm);
            taskPro.setState(isCheck ? "6" : "5");
            if (!isCheck) {
                taskPro.setSbv(value);
            } else {
                taskPro.setJhv(value);
            }
            source.add(taskPro);
        }
        return source;
    }

    public String getCkstate() {
        return ckstate;
    }

    public void setCkstate(String ckstate) {
        this.ckstate = ckstate;
    }

    public List<XcTaskPro> getSource() {
        return source;
    }

    public List<XcTaskPro> getOther() {
        return other;
    }
}
