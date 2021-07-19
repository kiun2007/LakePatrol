package com.kssoft.lake.net.responses.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.kssoft.lake.data.model.XcSnimdtF;

import java.util.Date;
import java.util.List;

import kiun.com.bvroutine.base.EventBean;

public class StationSampling extends EventBean {

    /**
     * 展开数据.
     * @param expansion true展开, false 收起.
     */
    private boolean expansion = false;

    /**
     * 地市名称
     */
    private String addvnm;

    /**
     * 站点名称
     */
    private String stnm;

    /**
     * 采样时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date spt;

    /**
     * 上报人
     */
    private String sbpnm;

    /**
     * 校核人
     */
    private String ckpnm;

    /**
     * 填报指标
     */
    private List<SamplingQuota> parm;

    /**
     * 填报图片
     */
    private List<XcSnimdtF> xcsnimdtf;

    public String getAddvnm() {
        return addvnm;
    }

    public void setAddvnm(String addvnm) {
        this.addvnm = addvnm;
    }

    public String getStnm() {
        return stnm;
    }

    public void setStnm(String stnm) {
        this.stnm = stnm;
    }

    public Date getSpt() {
        return spt;
    }

    public void setSpt(Date spt) {
        this.spt = spt;
    }

    public List<SamplingQuota> getParm() {
        return parm;
    }

    public void setParm(List<SamplingQuota> parm) {
        this.parm = parm;
    }

    public boolean isExpansion() {
        return expansion;
    }

    public void setExpansion(boolean expansion) {
        this.expansion = expansion;
        onChanged();
    }

    public String getSbpnm() {
        return sbpnm;
    }

    public void setSbpnm(String sbpnm) {
        this.sbpnm = sbpnm;
    }

    public String getCkpnm() {
        return ckpnm;
    }

    public void setCkpnm(String ckpnm) {
        this.ckpnm = ckpnm;
    }

    public List<XcSnimdtF> getXcsnimdtf() {
        return xcsnimdtf;
    }

    public void setXcsnimdtf(List<XcSnimdtF> xcsnimdtf) {
        this.xcsnimdtf = xcsnimdtf;
    }
}
