package com.kssoft.lake.data.model;

import kiun.com.bvroutine.base.EventBean;

public class CacheModel extends EventBean {

    private int count;

    private int progress;

    private String title;

    private String desc;

    private boolean complete = false;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        onChanged();
    }

    public CacheModel progress(int progress, int count){
        this.count = count;
        this.progress = progress;
        onChanged();
        return this;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        onChanged();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        onChanged();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
        onChanged();
    }

    public CacheModel desc(String desc){
        setDesc(desc);
        return this;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}
