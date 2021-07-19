package com.kssoft.lake.data.model.commit;

import com.kssoft.lake.R;
import com.kssoft.lake.data.QualityStandard;
import com.kssoft.lake.data.types.SamplingType;
import com.kssoft.lake.ui.activity.commit.CommitBaseActivity;
import com.kssoft.lake.ui.activity.commit.CommitUrgentActivity;

import java.io.Serializable;

import kiun.com.bvroutine.data.verify.NotNull;
import kiun.com.bvroutine.data.verify.RunCheck;
import kiun.com.bvroutine.interfaces.verify.Verify;
import kiun.com.bvroutine.interfaces.verify.Verifys;
import kiun.com.bvroutine.utils.MCString;

/**
 * 应急监测 实体类
 * 显示使用
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
public class XcWqnmispDetail extends QualityStandard implements Serializable {

    public XcWqnmispDetail() {
    }

    public XcWqnmispDetail(String var1) {
        super(var1);
    }

    public Class<? extends CommitBaseActivity> commitActivity() {
        return CommitUrgentActivity.clz;
    }

    public boolean isPass(String var1) {
        if ("0".equals(this.getSbtp())) {
            return MCString.isWith(var1, new Object[]{"algae", "redox"});
        } else {
            return "1".equals(this.getSbtp()) ? MCString.isWith(var1, new Object[]{"f", "cl"}) : false;
        }
    }

    public String itemTitle() {
        return this.formatItemTitle(SamplingType.Urgent.getTitle());
    }

    private Double algae;
    private Double cl;
    private Double codmn;
    private Double dox;
    private Double f;
    private Double nh3n;
    private Double ph;
    private Double q;
    private Double redox;
    private Double tp;
    private Double z;

    public Double getAlgae() {
        return this.algae;
    }

    public Double getCl() {
        return this.cl;
    }

    public Double getCodmn() {
        return this.codmn;
    }

    public Double getDox() {
        return this.dox;
    }

    public Double getF() {
        return this.f;
    }

    public Double getNh3n() {
        return this.nh3n;
    }

    public Double getPh() {
        return this.ph;
    }

    public Double getQ() {
        return this.q;
    }

    public Double getRedox() {
        return this.redox;
    }

    public Double getTp() {
        return this.tp;
    }

    public Double getZ() {
        return this.z;
    }

    public void setAlgae(Double var1) {
        this.algae = var1;
    }

    public void setCl(Double var1) {
        this.cl = var1;
    }

    public void setCodmn(Double var1) {
        this.codmn = var1;
    }

    public void setDox(Double var1) {
        this.dox = var1;
    }

    public void setF(Double var1) {
        this.f = var1;
    }

    public void setNh3n(Double var1) {
        this.nh3n = var1;
    }

    public void setPh(Double var1) {
        this.ph = var1;
    }

    public void setQ(Double var1) {
        this.q = var1;
    }

    public void setRedox(Double var1) {
        this.redox = var1;
    }

    public void setTp(Double var1) {
        this.tp = var1;
    }

    public void setZ(Double var1) {
        this.z = var1;
    }

    public int viewLayoutId() {
        return R.layout.layout_sampling_details_urgent;
    }

    @Override
    public String type() {
        return "3";
    }
}