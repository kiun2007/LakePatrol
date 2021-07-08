package com.kssoft.lake.data;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.Date;
import java.util.List;

import kiun.com.bvroutine.data.verify.Problem;
import kiun.com.bvroutine.utils.MCString;

/**
 * 水质国标.
 */
public abstract class QualityStandard extends SamplingBase {

    public QualityStandard(String rdcd) {
        super(rdcd);
    }

    public QualityStandard(){
        super();
    }

    //采样时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date spt;

    public Date getSpt() {
        return spt;
    }

    public void setSpt(Date spt) {
        this.spt = spt;
    }

    @Override
    public List<Problem> verify() {
        spt = new Date();
        return super.verify();
    }

    @Override
    public String itemTime(String format) {
        return MCString.formatDate(format, getSpt());
    }

    @Override
    public Date samplingTime() {
        return spt;
    }
}
