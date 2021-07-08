package com.kssoft.lake.net.responses.vo;

import android.annotation.SuppressLint;

import kiun.com.bvroutine.interfaces.view.ItemValue;

/**
 * 站点信息.
 */
public class AreaStBprp implements ItemValue {

    private String stcd;
    private String stnm;
    private double gap;

    /**
     * 测站编码
     * @return 编码
     */
    public String getStcd() {
        return stcd;
    }

    public void setStcd(String stcd) {
        this.stcd = stcd;
    }

    /**
     * 测站名称
     * @return 名称
     */
    public String getStnm() {
        return stnm;
    }

    public void setStnm(String stnm) {
        this.stnm = stnm;
    }

    /**
     * 获取当前位置到站点的距离
     * @return 距离.
     */
    public double getGap() {
        return gap;
    }

    public void setGap(double gap) {
        this.gap = gap;
    }


    @SuppressLint("DefaultLocale")
    public String getGapFormat(){
        if (gap > 1000){
            return String.format("%.1f公里", gap/1000);
        }
        return String.format("%.1f米", gap);
    }

    @Override
    public String itemId() {
        return stcd;
    }

    @Override
    public String itemTitle() {
        return stnm;
    }
}