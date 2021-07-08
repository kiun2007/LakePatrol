package com.kssoft.lake.net.requests.dto;

import android.annotation.SuppressLint;

import com.alibaba.fastjson.annotation.JSONField;
import com.kssoft.lake.data.LastLocation;
import com.kssoft.lake.data.model.XcSnimdtF;
import com.kssoft.lake.data.model.XcTaskP;
import com.kssoft.lake.data.types.RecordState;

import java.util.Date;
import java.util.List;

import kiun.com.bvroutine.base.EventBean;
import kiun.com.bvroutine.interfaces.JSON;
import kiun.com.bvroutine.utils.ListUtil;

/**
 * 巡测记录类
 */
public class XcRecdR extends EventBean implements JSON {

    //巡测记录代码
    private String rdcd;
    //任务代码
    private String tkcd;
    //开始巡测时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date srtm;
    //结束巡测时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date edtm;
    //开始巡测经度
    private Double srlgtd;
    //开始巡测纬度
    private Double srlttd;
    //结束巡测经度
    private Double edlgtd;
    //结束巡测纬度
    private Double edlttd;
    //巡测长度
    private Double len;
    //巡测耗时
    private Double spt;
    //巡测状态 0巡测中，1暂停，2校核中，3驳回，4完成巡测
    private int rdst;
    //创建记录人员代码
    private String rdctp;
    //巡测类型 0湖泛，1水文，2人工，3应急
    private String xctp;

    /**
     * 巡查人员信息.
     */
    private List<XcTaskP> xcTaskP;

    /**
     * 上报图片.
     */
    private List<XcSnimdtF> xcSnimdtF;

    @JSONField(serialize = false)
    public String getNames(){
        if (!ListUtil.isEmpty(xcTaskP)){
            List<String> names = ListUtil.toList(xcTaskP, item->item.getPnm());
            return ListUtil.join(names, ",");
        }
        return "未分配人员";
    }

    public void addMileage(Double len){
        this.len += len;
        onChanged();
    }

    @JSONField(serialize = false)
    public RecordState getState(){
        return RecordState.getValueOfIndex(rdst);
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

    public Date getSrtm() {
        return srtm;
    }

    public void setSrtm(Date srtm) {
        this.srtm = srtm;
    }

    public Date getEdtm() {
        return edtm;
    }

    public void setEdtm(Date edtm) {
        this.edtm = edtm;
        onChanged();
    }

    public Double getSrlgtd() {
        return srlgtd;
    }

    public void setSrlgtd(Double srlgtd) {
        this.srlgtd = srlgtd;
    }

    public Double getSrlttd() {
        return srlttd;
    }

    public void setSrlttd(Double srlttd) {
        this.srlttd = srlttd;
    }

    public Double getEdlgtd() {
        return edlgtd;
    }

    public void setEdlgtd(Double edlgtd) {
        this.edlgtd = edlgtd;
    }

    public Double getEdlttd() {
        return edlttd;
    }

    public void setEdlttd(Double edlttd) {
        this.edlttd = edlttd;
    }

    public Double getLen() {
        return len;
    }

    public void setLen(Double len) {
        this.len = len;
    }

    public Double getSpt() {
        return spt;
    }

    public void setSpt(Double spt) {
        this.spt = spt;
    }

    public int getRdst() {
        return rdst;
    }

    public void setRdst(int rdst) {
        this.rdst = rdst;
    }

    public String getRdctp() {
        return rdctp;
    }

    public void setRdctp(String rdctp) {
        this.rdctp = rdctp;
    }

    public String getXctp() {
        return xctp;
    }

    public void setXctp(String xctp) {
        this.xctp = xctp;
    }

    public List<XcTaskP> getXcTaskP() {
        return xcTaskP;
    }

    public void setXcTaskP(List<XcTaskP> xcTaskP) {
        this.xcTaskP = xcTaskP;
    }

    public List<XcSnimdtF> getXcSnimdtF() {
        return xcSnimdtF;
    }

    public void setXcSnimdtF(List<XcSnimdtF> xcSnimdtF) {
        this.xcSnimdtF = xcSnimdtF;
    }

    @SuppressLint("DefaultLocale")
    public String totalTime(){
        if (srtm == null){
            return "";
        }
        float m = ((float)new Date().getTime() - srtm.getTime()) / (60 * 1000);
        return String.format("%.2f分钟", m);
    }

    @SuppressLint("DefaultLocale")
    public String totalMileage(){
        if (len == null){
            return "0公里";
        }
        return String.format("%.2f公里", len/1000);
    }

    public XcRecdR pause(){
        rdst = 1;
        edtm = null;
        return this;
    }

    public XcRecdR resume(){
        rdst = 0;
        edtm = null;
        return this;
    }

    public XcRecdR begin(LastLocation lastLocation){
        srtm = new Date();
        srlttd = lastLocation.getLat();
        srlgtd = lastLocation.getLng();
        return this;
    }

    public XcRecdR stop(LastLocation lastLocation){
        edtm = new Date();
        rdst = 2;
        edlttd = lastLocation.getLat();
        edlgtd = lastLocation.getLng();
        spt = (double) (edtm.getTime() - srtm.getTime()) / 60000;
        return this;
    }
}
