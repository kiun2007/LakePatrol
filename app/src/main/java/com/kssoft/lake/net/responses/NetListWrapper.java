package com.kssoft.lake.net.responses;

import java.util.List;

import kiun.com.bvroutine.interfaces.wrap.ListWrap;

public class NetListWrapper<T> implements ListWrap<T> {

    private String code;

    private String msg;

    private int total;

    private List<T> rows;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public boolean isSuccess() {
        return "200".equals(code);
    }

    @Override
    public boolean isLogout() {
        return "401".equals(code);
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    @Override
    public List<T> wrapList() {
        return rows;
    }

    @Override
    public int pages() {
        return 0;
    }

    @Override
    public int total() {
        return total;
    }
}
