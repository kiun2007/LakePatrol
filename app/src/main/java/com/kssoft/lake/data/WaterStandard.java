package com.kssoft.lake.data;


import com.alibaba.fastjson.annotation.JSONField;
import java.util.Date;
import java.util.List;
import kiun.com.bvroutine.data.verify.Problem;
import kiun.com.bvroutine.utils.MCString;

/**
 * 水利标准.
 */
public abstract class WaterStandard extends SamplingBase {

    //采样时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date tm;

    public WaterStandard(String rdcd) {
        super(rdcd);
    }

    public WaterStandard(){
        super();
    }

    public Date getTm() {
        return tm;
    }

    public void setTm(Date tm) {
        this.tm = tm;
    }

    @Override
    public List<Problem> verify() {
        tm = new Date();
        return super.verify();
    }

    @Override
    public String itemTime(String format) {
        return MCString.formatDate(format, tm);
    }

    @Override
    public Date samplingTime() {
        return tm;
    }
}
