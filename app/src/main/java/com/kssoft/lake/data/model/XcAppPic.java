package com.kssoft.lake.data.model;


import com.kssoft.lake.data.BannerBean;

public class XcAppPic implements BannerBean {

    //编码
    private String icd;
    //名称
    private String inm;
    //是否在手机上显示
    private String isdpl;
    //图大小
    private String flsz;
    //创建时间
    private String tm;

    public String getIcd() {
        return icd;
    }

    public void setIcd(String icd) {
        this.icd = icd;
    }

    public String getInm() {
        return inm;
    }

    public void setInm(String inm) {
        this.inm = inm;
    }

    public String getIsdpl() {
        return isdpl;
    }

    public void setIsdpl(String isdpl) {
        this.isdpl = isdpl;
    }

    public String getFlsz() {
        return flsz;
    }

    public void setFlsz(String flsz) {
        this.flsz = flsz;
    }

    public String getTm() {
        return tm;
    }

    public void setTm(String tm) {
        this.tm = tm;
    }

    @Override
    public String bannerUrl() {
        return inm;
    }
}
