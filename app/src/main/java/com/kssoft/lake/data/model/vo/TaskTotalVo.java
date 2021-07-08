package com.kssoft.lake.data.model.vo;

/**
 * APP 领导界面 中间部分 今日计划总数   返回参数类
 */
public class TaskTotalVo {

    private int g; //组数
    private int t; //总计划站点数
    private int x; //已巡站点数
    private int w; // 未巡站点数
    private int p; //总人员数

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }
}
