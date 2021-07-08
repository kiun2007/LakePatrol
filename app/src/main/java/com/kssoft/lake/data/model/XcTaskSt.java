package com.kssoft.lake.data.model;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * 巡测任务分配测站类
 */
public class XcTaskSt {

    /**
     * 任务代码
     */
    private String tkcd;


    /**
     * 测站代码
     */
    private String stcd;

    /**
     * 测站名称
     */
    private String stnm;

    /**
     * 任务状态.
     */
    private String state;

    public String getTkcd() {
        return tkcd;
    }

    public void setTkcd(String tkcd) {
        this.tkcd = tkcd;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
