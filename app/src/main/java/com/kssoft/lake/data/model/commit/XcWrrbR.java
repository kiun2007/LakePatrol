package com.kssoft.lake.data.model.commit;

import com.alibaba.fastjson.annotation.JSONField;
import com.kssoft.lake.R;
import com.kssoft.lake.data.WaterStandard;
import com.kssoft.lake.data.types.SamplingType;
import com.kssoft.lake.ui.activity.commit.CommitBaseActivity;
import com.kssoft.lake.ui.activity.commit.CommitManualActivity;

import kiun.com.bvroutine.data.verify.NotNull;
import kiun.com.bvroutine.data.verify.RunCheck;
import kiun.com.bvroutine.interfaces.verify.Verify;
import kiun.com.bvroutine.interfaces.verify.Verifys;
import kiun.com.bvroutine.utils.ObjectUtil;

import java.io.Serializable;

/**
 * 人工观测 实体类
 */
@Verifys({@Verify(
        desc = "值不能为空",
        pass = "that.isPass(fieldName)",
        value = NotNull.class
), @Verify(
        desc = "numberChecker.descES(lakeLimit, fieldName, extraResult)",
        extra = "numberChecker.checkES(that, lakeLimit, fieldName)",
        force = false,
        pass = "that.isPass(fieldName)",
        value = RunCheck.class
)})
public class XcWrrbR extends WaterStandard implements Serializable {
    private Double atmp;
    private Double dwz;
    private String eetp;
    private Double gtophgt;
    private Integer gtopnum;
    private Double obhgt;
    private Double p;
    private Double q;
    private Double s;
    private Double tdq;
    private Double tdw;
    private Double tdz;
    private Double upz;
    private Double vp;
    private Double vpd;
    private Double w;
    private Double wndv;
    private Double wsfe;
    private Double wtmp;

    public XcWrrbR() {
    }

    public XcWrrbR(String var1) {
        super(var1);
    }

    public Class<? extends CommitBaseActivity> commitActivity() {
        return CommitManualActivity.clz;
    }

    public Double getAtmp() {
        return this.atmp;
    }

    public Double getDwz() {
        return this.dwz;
    }

    public String getEetp() {
        return this.eetp;
    }

    public Double getGtophgt() {
        return this.gtophgt;
    }

    public Integer getGtopnum() {
        return this.gtopnum;
    }

    public Double getObhgt() {
        return this.obhgt;
    }

    public Double getP() {
        return this.p;
    }

    public Double getQ() {
        return this.q;
    }

    public Double getS() {
        return this.s;
    }

    public Double getTdq() {
        return this.tdq;
    }

    public Double getTdw() {
        return this.tdw;
    }

    public Double getTdz() {
        return this.tdz;
    }

    public Double getUpz() {
        return this.upz;
    }

    public Double getVp() {
        return this.vp;
    }

    public Double getVpd() {
        return this.vpd;
    }

    public Double getW() {
        return this.w;
    }

    public Double getWndv() {
        return this.wndv;
    }

    public Double getWsfe() {
        return this.wsfe;
    }

    public Double getWtmp() {
        return this.wtmp;
    }

    @JSONField(
            serialize = false
    )
    public boolean isCheck(String var1) {
        return "remark".endsWith(var1) ? true : ObjectUtil.isEmpty(this) ^ true;
    }

    public boolean isPass(String fieldName){
        return false;
    }

    public String itemTitle() {
        return this.formatItemTitle(SamplingType.Manual.getTitle());
    }

    public void setAtmp(Double var1) {
        this.atmp = var1;
    }

    public void setDwz(Double var1) {
        this.dwz = var1;
    }

    public void setEetp(String var1) {
        this.eetp = var1;
    }

    public void setGtophgt(Double var1) {
        this.gtophgt = var1;
    }

    public void setGtopnum(Integer var1) {
        this.gtopnum = var1;
    }

    public void setObhgt(Double var1) {
        this.obhgt = var1;
    }

    public void setP(Double var1) {
        this.p = var1;
    }

    public void setQ(Double var1) {
        this.q = var1;
    }

    public void setS(Double var1) {
        this.s = var1;
    }

    public void setTdq(Double var1) {
        this.tdq = var1;
    }

    public void setTdw(Double var1) {
        this.tdw = var1;
    }

    public void setTdz(Double var1) {
        this.tdz = var1;
    }

    public void setUpz(Double var1) {
        this.upz = var1;
    }

    public void setVp(Double var1) {
        this.vp = var1;
    }

    public void setVpd(Double var1) {
        this.vpd = var1;
    }

    public void setW(Double var1) {
        this.w = var1;
    }

    public void setWndv(Double var1) {
        this.wndv = var1;
    }

    public void setWsfe(Double var1) {
        this.wsfe = var1;
    }

    public void setWtmp(Double var1) {
        this.wtmp = var1;
    }

    public int viewLayoutId() {
        return R.layout.layout_sampling_details_manual;
    }
}