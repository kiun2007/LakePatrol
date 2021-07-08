package com.kssoft.lake.data.model.vo;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.text.TextUtils;

import com.kssoft.lake.MainApplication;
import com.kssoft.lake.R;
import com.kssoft.lake.base.presenter.GraphicsPoint;

import kiun.com.bvroutine.utils.BitmapUtil;
import kiun.com.bvroutine.utils.LocationUtils;

/**
 * APP地图界面 站点状态和数据查询
 */
public class MapStateVo implements GraphicsPoint {

    static int[] legendMipMaps = new int[]{
            //湖泛图例 已 未 警
            R.mipmap.ic_legend_hufan_inspected,
            R.mipmap.ic_legend_hufan_not_inspected,
            R.mipmap.ic_legend_hufan_abnormal,
            //水文图例 已 未 警
            R.mipmap.ic_legend_shuiwen_inspected,
            R.mipmap.ic_legend_shuiwen_not_inspected,
            R.mipmap.ic_legend_shuiwen_abnormal,
            //人工图例 已 未 警
            R.mipmap.ic_legend_rengong2,
            R.mipmap.ic_legend_rengong,
            R.mipmap.ic_map_hufan_abnormal2,
            //应急图例
            R.mipmap.ic_legend_shuiwen_abnormal
    };

    private String stcd; //测站编码
    private String stnm; //测站名称

    private double lgtd; //经度
    private double lttd; //纬度

    private String xctp; //测站类型
    private String xctpnm; //测站类型

    private String state = "0";//巡测状态 1已巡 0未巡
    private String statenm;//巡测状态中文

    private String rdcd; //巡测记录编码
    private String tkcd;//巡测任务编码
    private int iswrz;//是否超警 1 超 0未超

    public Location toLocation(){
        return LocationUtils.addLocation(lttd, lgtd);
    }

    public int legendIndex(){

        if (!TextUtils.isEmpty(xctp)){
            int baseIndex = Integer.parseInt(xctp) * 3;
            if (baseIndex == 9){
                return baseIndex;
            }
            baseIndex += (iswrz == 1 ? 2 : ("1".equals(state) ? 0 : 1));
            return baseIndex;
        }
        return -1;
    }

    public int getMipMap(){
        int legendIndex = legendIndex();
        if (legendIndex < 0){
            return -1;
        }
        return legendMipMaps[legendIndex];
    }

    public String getStcd() {
        return stcd;
    }

    public void setStcd(String stcd) {
        this.stcd = stcd;
    }

    public String getStnm() {
        return stnm;
    }

    public void setStnm(String stnm) {
        this.stnm = stnm;
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

    public String getXctp() {
        return xctp;
    }

    public void setXctp(String xctp) {
        this.xctp = xctp;
    }

    public String getXctpnm() {
        return xctpnm;
    }

    public void setXctpnm(String xctpnm) {
        this.xctpnm = xctpnm;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatenm() {
        return statenm;
    }

    public void setStatenm(String statenm) {
        this.statenm = statenm;
    }

    public String getRdcd() {
        return rdcd;
    }

    public void setRdcd(String rdcd) {
        this.rdcd = rdcd;
    }

    public String getTkcd() {
        return tkcd;
    }

    public void setTkcd(String tkcd) {
        this.tkcd = tkcd;
    }

    public int getIswrz() {
        return iswrz;
    }

    public void setIswrz(int iswrz) {
        this.iswrz = iswrz;
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
        return stcd;
    }

    @Override
    public int count() {
        return 1;
    }
}
