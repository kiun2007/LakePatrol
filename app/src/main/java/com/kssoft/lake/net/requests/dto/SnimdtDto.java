package com.kssoft.lake.net.requests.dto;


import com.kssoft.lake.data.LocationBean;
import com.kssoft.lake.data.SnimdtDtoVariableSet;
import kiun.com.bvroutine.base.binding.variable.AutoImport;
import kiun.com.bvroutine.data.QueryBean;

/**
 * 多媒体参数类
 */
@AutoImport(SnimdtDtoVariableSet.class)
public class SnimdtDto extends QueryBean implements LocationBean {

    private int xctp; //巡测类型 1湖泛，2水文，3人工，4应急
    private double lgtd;//经度
    private double lttd; //纬度

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

    @Override
    public double latitude() {
        return lttd;
    }

    @Override
    public double longitude() {
        return lgtd;
    }

    @Override
    public void latitude(double lat) {
        lttd = lat;
    }

    @Override
    public void longitude(double lng) {
        lgtd = lng;
    }

    public int getXctp() {
        return xctp;
    }

    public void setXctp(int xctp) {
        this.xctp = xctp;
    }
}
