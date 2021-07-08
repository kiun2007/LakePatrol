package com.kssoft.lake.net.responses.vo;

import java.util.Date;
import java.util.List;

/**
 * 简报实体类
 */
public class ReportVo {

    private String rcd; //简报编号
    private Date srtm; //时间
    private String tm; //日期
    private int num; //序号
    private String title; //标题
    private String rnm; //简报名称
    private String ep; //编制人编号
    private String epnm; //编制人代码
    private String sbp; //上报人编号
    private String sbpnm; //上报人名称
    private String ckp; //校核人代码
    private String ckpnm; //校核人名称
    private String exp; //审核人代码
    private String expnm; //审核人名称
    private String rst; //简报状态代码 0待校核，1校核驳回，2待审核，3审核驳回 4完成
    private String rstnm; //简报状态中文  待校核，校核驳回，待审核，审核驳回 完成
    private String wktp; //周
    private String nt; //驳回理由
    private String rtp; //简报类型代码 0湖泛巡测，1应急监测
    private String rtpnm; //简报类型中文 湖泛巡测 应急监测

    //非数据库字段
    List<XcReportP> xcReportP;//校核审核人信息

    public String getRcd() {
        return rcd;
    }

    public void setRcd(String rcd) {
        this.rcd = rcd;
    }

    public Date getSrtm() {
        return srtm;
    }

    public void setSrtm(Date srtm) {
        this.srtm = srtm;
    }

    public String getTm() {
        return tm;
    }

    public void setTm(String tm) {
        this.tm = tm;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRnm() {
        return rnm;
    }

    public void setRnm(String rnm) {
        this.rnm = rnm;
    }

    public String getEp() {
        return ep;
    }

    public void setEp(String ep) {
        this.ep = ep;
    }

    public String getEpnm() {
        return epnm;
    }

    public void setEpnm(String epnm) {
        this.epnm = epnm;
    }

    public String getSbp() {
        return sbp;
    }

    public void setSbp(String sbp) {
        this.sbp = sbp;
    }

    public String getSbpnm() {
        return sbpnm;
    }

    public void setSbpnm(String sbpnm) {
        this.sbpnm = sbpnm;
    }

    public String getCkp() {
        return ckp;
    }

    public void setCkp(String ckp) {
        this.ckp = ckp;
    }

    public String getCkpnm() {
        return ckpnm;
    }

    public void setCkpnm(String ckpnm) {
        this.ckpnm = ckpnm;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getExpnm() {
        return expnm;
    }

    public void setExpnm(String expnm) {
        this.expnm = expnm;
    }

    public String getRst() {
        return rst;
    }

    public void setRst(String rst) {
        this.rst = rst;
    }

    public String getRstnm() {
        return rstnm;
    }

    public void setRstnm(String rstnm) {
        this.rstnm = rstnm;
    }

    public String getWktp() {
        return wktp;
    }

    public void setWktp(String wktp) {
        this.wktp = wktp;
    }

    public String getNt() {
        return nt;
    }

    public void setNt(String nt) {
        this.nt = nt;
    }

    public String getRtp() {
        return rtp;
    }

    public void setRtp(String rtp) {
        this.rtp = rtp;
    }

    public String getRtpnm() {
        return rtpnm;
    }

    public void setRtpnm(String rtpnm) {
        this.rtpnm = rtpnm;
    }

    public List<XcReportP> getXcReportP() {
        return xcReportP;
    }

    public void setXcReportP(List<XcReportP> xcReportP) {
        this.xcReportP = xcReportP;
    }
}
