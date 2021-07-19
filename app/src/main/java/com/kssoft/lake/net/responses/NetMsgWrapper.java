package com.kssoft.lake.net.responses;

import com.alibaba.fastjson.JSON;
import com.kssoft.lake.net.responses.vo.StationSampling;
import com.kssoft.lake.net.responses.vo.XcTaskPro;

import java.util.List;

import kiun.com.bvroutine.interfaces.wrap.DataWrap;

public class NetMsgWrapper implements DataWrap<List<StationSampling>> {

    private String code;

    private String msg;

    @Override
    public List<StationSampling> getData() {
        List<StationSampling> sampling = JSON.parseArray(msg, StationSampling.class);
        return sampling;
    }

    @Override
    public String getMsg() {
        return null;
    }

    @Override
    public boolean isSuccess() {
        return "200".equals(code);
    }

    @Override
    public boolean isLogout() {
        return false;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
