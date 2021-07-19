package com.kssoft.lake.net.requests.dto;


import com.alibaba.fastjson.annotation.JSONField;
import com.kssoft.lake.data.SamplingBase;
import com.kssoft.lake.data.types.SamplingType;
import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.interfaces.TypeRequest;

public class DataDto extends PagerBean<Object,DataDto> implements TypeRequest{

    private String stcd; //测站编码
    private String xctp; //巡测类型 0湖泛，1水文，2人工，3应急
    private String tkcd; //任务代码
    private String rdcd; //巡查记录

    public DataDto(){}

    public DataDto(String stcd, String xctp, String tkcd) {
        this.stcd = stcd;
        this.xctp = xctp;
        this.tkcd = tkcd;
    }

    public String getStcd() {
        return stcd;
    }

    public void setStcd(String stcd) {
        this.stcd = stcd;
    }

    public String getXctp() {
        return xctp;
    }

    public void setXctp(String xctp) {
        this.xctp = xctp;
        onChanged(false);
    }

    public String getTkcd() {
        return tkcd;
    }

    public void setTkcd(String tkcd) {
        this.tkcd = tkcd;
    }

    public String getRdcd() {
        return rdcd;
    }

    public void setRdcd(String rdcd) {
        this.rdcd = rdcd;
    }

    @JSONField(serialize = false)
    public SamplingType getSamplingType(){
        return SamplingType.getType(xctp);
    }

    @JSONField(serialize = false)
    public String getItemClzName(){
        Class clz = getSamplingType().getClz();
        if (clz != null){
            return clz.getName();
        }
        return null;
    }

    @Override
    @JSONField(serialize = false)
    public Class<? extends SamplingBase> getType() {
        return getSamplingType().getClz();
    }
}
