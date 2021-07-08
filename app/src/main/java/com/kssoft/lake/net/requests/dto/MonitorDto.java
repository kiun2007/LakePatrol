package com.kssoft.lake.net.requests.dto;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import kiun.com.bvroutine.base.EventBean;
import kiun.com.bvroutine.utils.MCString;

public class MonitorDto extends EventBean {

    /**
     * 时间
     */
    private String tm;

    /**
     * 类型.
     */
    private List<String> types = new LinkedList<>();

    public MonitorDto(){
        tm = MCString.formatDate("yyyy-MM-dd", new Date());
        types.add("0");
        types.add("1");
        types.add("2");
        types.add("3");
    }

    public String getTm() {
        return tm;
    }

    public void setTm(String tm) {
        this.tm = tm;
        onChanged(false);
    }

    public void checkType(String type, boolean isChecked){
        if (isChecked){
            types.add(type);
        }else{
            types.remove(type);
        }
        onChanged(false);
    }

    public boolean isChecked(String type){
        return types.contains(type);
    }
}
