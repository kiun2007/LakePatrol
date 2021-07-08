package com.kssoft.lake.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

/**
 * 巡测多媒体文件 实体类
 */
public class XcSnimdtF implements Serializable {

    /**
     * 文件编号
     */
    private String fcd;
    //上传时间
    private Date tm;
    //文件名称
    private String flnm;
    //文件大小
    private double flsz;
    //图片经度
    private double lgtd;
    //图片纬度
    private double lttd;
    //文件扩展名
    private String flext;
    //上传人代码
    private String sbp;
    //文件类型 0图片，1视频，2语音
    private String ftp;

    public XcSnimdtF(){}

    public String getFcd() {
        return fcd;
    }

    public void setFcd(String fcd) {
        this.fcd = fcd;
    }

    public Date getTm() {
        return tm;
    }

    public void setTm(Date tm) {
        this.tm = tm;
    }

    public String getFlnm() {
        return flnm;
    }

    public void setFlnm(String flnm) {
        this.flnm = flnm;
    }

    public double getFlsz() {
        return flsz;
    }

    public void setFlsz(double flsz) {
        this.flsz = flsz;
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

    public String getFlext() {
        return flext;
    }

    public void setFlext(String flext) {
        this.flext = flext;
    }

    public String getSbp() {
        return sbp;
    }

    public void setSbp(String sbp) {
        this.sbp = sbp;
    }

    public String getFtp() {
        return ftp;
    }

    public void setFtp(String ftp) {
        this.ftp = ftp;
    }

    public String getThumb(){
        return String.format("%s.thumb.jpg",flnm);
    }
}
