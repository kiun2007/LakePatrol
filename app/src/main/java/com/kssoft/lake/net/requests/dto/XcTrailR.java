package com.kssoft.lake.net.requests.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.kssoft.lake.base.presenter.GraphicsPoint;

import java.util.Date;

/**
 * 巡测轨迹 实体类
 */
public class XcTrailR implements GraphicsPoint {

    //巡测记录代码
    private String rdcd;
    //巡测经度
    private double lgtd;
    //巡测纬度
    private double lttd;
    //实时地址
    private String addvnm;
    //创建时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date tm;

    public String getRdcd() {
        return rdcd;
    }

    public void setRdcd(String rdcd) {
        this.rdcd = rdcd;
    }

    public double getLgtd() {
        return lgtd;
    }

    public void setLgtd(double lgtd) {
        this.lgtd = lgtd;
    }

    public double getLttd() {
        return lttd;
    }

    public void setLttd(double lttd) {
        this.lttd = lttd;
    }

    public String getAddvnm() {
        return addvnm;
    }

    public void setAddvnm(String addvnm) {
        this.addvnm = addvnm;
    }

    public Date getTm() {
        return tm;
    }

    public void setTm(Date tm) {
        this.tm = tm;
    }

    @Override
    public double getLat() {
        return lttd;
    }

    @Override
    public double getLng() {
        return lgtd;
    }

    @Override
    public String key() {
        return null;
    }

    @Override
    public int count() {
        return 0;
    }
}
