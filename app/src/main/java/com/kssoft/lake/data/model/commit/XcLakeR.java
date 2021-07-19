package com.kssoft.lake.data.model.commit;

import com.alibaba.fastjson.annotation.JSONField;
import com.kssoft.lake.R;
import com.kssoft.lake.data.QualityStandard;
import com.kssoft.lake.data.types.SamplingType;
import com.kssoft.lake.ui.activity.commit.CommitBaseActivity;
import com.kssoft.lake.ui.activity.commit.CommitLakeActivity;

import java.io.Serializable;

import kiun.com.bvroutine.data.verify.NotNull;
import kiun.com.bvroutine.data.verify.RunCheck;
import kiun.com.bvroutine.interfaces.JSON;
import kiun.com.bvroutine.interfaces.verify.Verify;
import kiun.com.bvroutine.interfaces.verify.Verifys;

/**
 * 湖泛巡查 实体类
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
public class XcLakeR extends QualityStandard implements JSON, Serializable {

    private Double airt;
    private Integer algae05;
    private Integer algae10;
    private Integer algae30;
    private Integer algae50;
    private String algaest;
    private Double atm;
    private Double chla;
    private Double chla10;
    private Double chla30;
    private Double chla50;
    private Integer clarity;
    private Integer cond;
    @JSONField(
            format = ".1"
    )
    private Double dof;
    private Double dox;
    private Double doxd;
    private String gaqp;
    private String gaqpam;
    private Double ph;
    private Integer redox;
    private String smell;

    private Double wndv;
    private String wnddir;
    private Integer wndpwr;
    private Double wt;
    private String wtcl;
    private Double wtd;
    private String wth;

    public XcLakeR() {
    }

    public XcLakeR(String var1) {
        super(var1);
    }

    private void computeDof() {
        if (this.atm != null && this.wt != null) {
            Double var1 = this.dox;
            if (var1 != null) {
                this.dof = var1 / (this.atm / 101.2D * (477.8D / (this.wt + 32.26D))) * 100.0D;
                this.onChanged();
            }
        }

    }

    public Class<? extends CommitBaseActivity> commitActivity() {
        return CommitLakeActivity.clz;
    }

    public Double getAirt() {
        return this.airt;
    }

    public Integer getAlgae05() {
        return this.algae05;
    }

    public Integer getAlgae10() {
        return this.algae10;
    }

    public Integer getAlgae30() {
        return this.algae30;
    }

    public Integer getAlgae50() {
        return this.algae50;
    }

    public String getAlgaest() {
        return this.algaest;
    }

    public Double getAtm() {
        return this.atm;
    }

    public Double getChla10() {
        return this.chla10;
    }

    public Double getChla30() {
        return this.chla30;
    }

    public Double getChla50() {
        return this.chla50;
    }

    public Integer getClarity() {
        return this.clarity;
    }

    public Integer getCond() {
        return this.cond;
    }

    public Double getDof() {
        return this.dof;
    }

    public Double getDox() {
        return this.dox;
    }

    public Double getDoxd() {
        return this.doxd;
    }

    public String getGaqp() {
        return this.gaqp;
    }

    public String getGaqpam() {
        return this.gaqpam;
    }

    public Double getPh() {
        return this.ph;
    }

    public Integer getRedox() {
        return this.redox;
    }

    public String getSmell() {
        return this.smell;
    }

    public String getWnddir() {
        return this.wnddir;
    }

    public Integer getWndpwr() {
        return this.wndpwr;
    }

    public Double getWt() {
        return this.wt;
    }

    public String getWtcl() {
        return this.wtcl;
    }

    public Double getWtd() {
        return this.wtd;
    }

    public String getWth() {
        return this.wth;
    }

    public boolean isPass(String var1) {
        return !"1".equals(this.gaqp) && "gaqpam".equals(var1);
    }

    public String itemTitle() {
        return this.formatItemTitle(SamplingType.Lake.getTitle());
    }

    @Override
    public String type() {
        return "0";
    }

    public void setAirt(Double var1) {
        this.airt = var1;
    }

    public void setAlgae05(Integer var1) {
        this.algae05 = var1;
    }

    public void setAlgae10(Integer var1) {
        this.algae10 = var1;
    }

    public void setAlgae30(Integer var1) {
        this.algae30 = var1;
    }

    public void setAlgae50(Integer var1) {
        this.algae50 = var1;
    }

    public void setAlgaest(String var1) {
        this.algaest = var1;
        this.onChanged();
    }

    public void setAtm(Double var1) {
        this.atm = var1;
        this.computeDof();
    }

    public Double getChla() {
        return chla;
    }

    public void setChla(Double chla) {
        this.chla = chla;
    }

    public void setChla10(Double var1) {
        this.chla10 = var1;
    }

    public void setChla30(Double var1) {
        this.chla30 = var1;
    }

    public void setChla50(Double var1) {
        this.chla50 = var1;
    }

    public void setClarity(Integer var1) {
        this.clarity = var1;
    }

    public void setCond(Integer var1) {
        this.cond = var1;
    }

    public void setDof(Double var1) {
        this.dof = var1;
    }

    public void setDox(Double var1) {
        this.dox = var1;
        this.computeDof();
    }

    public void setDoxd(Double var1) {
        this.doxd = var1;
    }

    public void setGaqp(String var1) {
        this.gaqp = var1;
        this.onChanged();
    }

    public void setGaqpam(String var1) {
        this.gaqpam = var1;
        this.onChanged();
    }

    public void setPh(Double var1) {
        this.ph = var1;
    }

    public void setRedox(Integer var1) {
        this.redox = var1;
    }

    public void setSmell(String var1) {
        this.smell = var1;
        this.onChanged();
    }

    public Double getWndv() {
        return wndv;
    }

    public void setWndv(Double wndv) {
        this.wndv = wndv;
        if (wndv != null) {
            double[] var3 = new double[]{0.0D, 0.3D, 1.6D, 3.4D, 5.5D, 8.0D, 10.8D, 13.9D, 17.2D, 20.8D, 24.5D, 28.5D, 32.7D};
            int var2 = 0;

            while(true) {
                this.wndpwr = var2;
                if (this.wndpwr >= var3.length || wndv < var3[this.wndpwr]) {
                    this.wndpwr = this.wndpwr - 1;
                    this.onChanged();
                    break;
                }

                var2 = this.wndpwr + 1;
            }
        }
    }

    public void setWnddir(String var1) {
        this.wnddir = var1;
    }

    public void setWndpwr(Integer var1) {
        this.wndpwr = var1;
    }

    public void setWt(Double var1) {
        this.wt = var1;
        this.computeDof();
    }

    public void setWtcl(String var1) {
        this.wtcl = var1;
        this.onChanged();
    }

    public void setWtd(Double var1) {
        this.wtd = var1;
    }

    public void setWth(String var1) {
        this.wth = var1;
        this.onChanged();
    }

    public int viewLayoutId() {
        return R.layout.layout_sampling_details_lake;
    }
}
