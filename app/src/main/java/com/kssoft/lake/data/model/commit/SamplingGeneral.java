package com.kssoft.lake.data.model.commit;

import com.alibaba.fastjson.annotation.JSONField;
import com.kssoft.lake.data.SamplingBase;
import com.kssoft.lake.data.types.SamplingType;
import com.kssoft.lake.ui.activity.commit.CommitBaseActivity;
import com.kssoft.lake.ui.activity.commit.CommitGeneralActivity;
import com.province.libcacheline.utils.MCString;

import java.util.Date;

public class SamplingGeneral extends SamplingBase {

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date tm;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date spt;

    private String xctp;

    public SamplingGeneral() {
    }

    public SamplingGeneral(String rdcd) {
        super(rdcd);
    }

    @Override
    public Class<? extends CommitBaseActivity> commitActivity() {
        return CommitGeneralActivity.class;
    }

    @Override
    public int viewLayoutId() {
        return 0;
    }

    @Override
    public String itemTitle() {
        return this.formatItemTitle(SamplingType.getType(xctp).getTitle());
    }

    @Override
    public String itemTime(String format) {
        if (spt != null){
            return MCString.formatDate(format, spt);
        }
        return MCString.formatDate(format, tm);
    }

    @Override
    public Date samplingTime() {
        return tm != null ? tm : spt;
    }

    @Override
    public String type() {
        return xctp;
    }

    public Date getTm() {
        return tm;
    }

    public void setTm(Date tm) {
        this.tm = tm;
    }

    public Date getSpt() {
        return spt;
    }

    public void setSpt(Date spt) {
        this.spt = spt;
    }

    public String getXctp() {
        return xctp;
    }

    public void setXctp(String xctp) {
        this.xctp = xctp;
    }
}
