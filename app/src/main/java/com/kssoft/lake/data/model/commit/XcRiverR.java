package com.kssoft.lake.data.model.commit;

import com.kssoft.lake.R;
import com.kssoft.lake.data.WaterStandard;
import com.kssoft.lake.data.types.SamplingType;
import com.kssoft.lake.net.services.DataService;
import com.kssoft.lake.ui.activity.commit.CommitBaseActivity;
import com.kssoft.lake.ui.activity.commit.CommitHydrologyActivity;

import kiun.com.bvroutine.data.verify.NotNull;
import kiun.com.bvroutine.data.verify.RunCheck;
import kiun.com.bvroutine.interfaces.verify.Verify;
import kiun.com.bvroutine.interfaces.verify.Verifys;
import retrofit2.Call;

import java.io.Serializable;

/**
 * 水文巡测 实体类
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
public class XcRiverR extends WaterStandard implements Serializable {

    private Double q;
    private Double z;

    public XcRiverR() {
    }

    public XcRiverR(String var1) {
        super(var1);
    }

    public Class<? extends CommitBaseActivity> commitActivity() {
        return CommitHydrologyActivity.clz;
    }

    public boolean isPass(String fieldName){
        return false;
    }

    public Double getQ() {
        return this.q;
    }

    public Double getZ() {
        return this.z;
    }

    public String itemTitle() {
        return this.formatItemTitle(SamplingType.Hydrology.getTitle());
    }

    @Override
    public String type() {
        return "1";
    }

    public void setQ(Double var1) {
        this.q = var1;
    }

    public void setZ(Double var1) {
        this.z = var1;
    }

    public int viewLayoutId() {
        return R.layout.layout_sampling_details_hydrology;
    }
}
