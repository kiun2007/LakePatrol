package com.kssoft.lake.data.model;

import java.io.Serializable;

/**
 * 巡测任务分配人员类
 */
public class XcTaskP implements Serializable {
    /**
     * 任务代码
     */
    private String tkcd;

    /**
     * 人员代码
     */
    private String pcd;

    /**
     * 人员名称
     */
    private String pnm;

    public XcTaskP(){}


    public String getTkcd() {
        return tkcd;
    }

    public void setTkcd(String tkcd) {
        this.tkcd = tkcd;
    }

    public String getPcd() {
        return pcd;
    }

    public void setPcd(String pcd) {
        this.pcd = pcd;
    }

    public String getPnm() {
        return pnm;
    }

    public void setPnm(String pnm) {
        this.pnm = pnm;
    }
}
