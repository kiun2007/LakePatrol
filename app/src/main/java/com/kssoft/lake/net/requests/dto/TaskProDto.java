package com.kssoft.lake.net.requests.dto;

import kiun.com.bvroutine.data.QueryBean;

public class TaskProDto extends QueryBean {

    private String stcd;//测站编码
    private String tm; //日期

    public String getStcd() {
        return stcd;
    }

    public void setStcd(String stcd) {
        this.stcd = stcd;
    }

    public String getTm() {
        return tm;
    }

    public void setTm(String tm) {
        this.tm = tm;
    }
}
