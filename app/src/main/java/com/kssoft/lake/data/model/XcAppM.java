package com.kssoft.lake.data.model;

public class XcAppM {

    //模块编码
    private String mcd;
    //模块名称
    private String mnm;
    //模块状态
    private String state;

    private String num;

    //模块是否隐藏
    private String hide;

    public String getMcd() {
        return mcd;
    }

    public void setMcd(String mcd) {
        this.mcd = mcd;
    }

    public String getMnm() {
        return mnm;
    }

    public void setMnm(String mnm) {
        this.mnm = mnm;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getHide() {
        return hide;
    }

    public void setHide(String hide) {
        this.hide = hide;
    }
}
