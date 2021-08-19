package com.kssoft.lake.net.responses.vo;

import android.text.TextUtils;

import kiun.com.bvroutine.base.EventBean;

public class XcTaskPro extends EventBean {

    private String id; // ID编号
    private String stcd; //测站编码
    private String tm; // 时间
    private String enname;//字段标识
    private String chname;//字段名称
    private String sbv; //上报的第一个值
    private String jhv; // 上报的第二个值
    private String sttp;  //测站类型
    private String state; //校核状态 5 录入  6 校核(第二遍输入) 7同意 8驳回
    private String sbcd; // 上报用户
    private String jhcd; //校核用户
    private String flag; //是否选中
    private String rdcd;
    private int sort;//排序
    private String code;
    private String stnm;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStnm() {
        return stnm;
    }

    public void setStnm(String stnm) {
        this.stnm = stnm;
    }

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

    public String getEnname() {
        return enname;
    }

    public void setEnname(String enname) {
        this.enname = enname;
    }

    public String getChname() {
        return chname;
    }

    public void setChname(String chname) {
        this.chname = chname;
    }

    public String getSbv() {
        return sbv;
    }

    public void setSbv(String sbv) {
        this.sbv = sbv;
    }

    public String getJhv() {
        return jhv;
    }

    public void setJhv(String jhv) {
        this.jhv = jhv;
    }

    public String getSttp() {
        return sttp;
    }

    public void setSttp(String sttp) {
        this.sttp = sttp;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
        if("6".equals(state)){
            jhv = sbv;
        }
    }

    public String getSbcd() {
        return sbcd;
    }

    public void setSbcd(String sbcd) {
        this.sbcd = sbcd;
    }

    public String getJhcd() {
        return jhcd;
    }

    public void setJhcd(String jhcd) {
        this.jhcd = jhcd;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getRdcd() {
        return rdcd;
    }

    public void setRdcd(String rdcd) {
        this.rdcd = rdcd;
    }

    public boolean isCheckPass(){
        return jhv == null || jhv.equals(sbv);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isPass(){
        return !TextUtils.isEmpty(sbv) || !TextUtils.isEmpty(jhv);
    }
}
