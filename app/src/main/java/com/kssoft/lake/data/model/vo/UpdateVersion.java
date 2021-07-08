package com.kssoft.lake.data.model.vo;

import kiun.com.bvroutine.base.EventBean;

public class UpdateVersion extends EventBean {

    public enum State{
        Downloading("下载中"),
        Downloaded("下载完成"),
        DownloadError("下载出错了"),
        Installing("安装中"),
        Complete("完成");

        private String title;
        State(String title){
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }

    private Long count = 0l;

    private Long current = 0l;

    private String appName;

    private String versionDesc;

    private String versionName;

    private State state = State.Downloading;

    public UpdateVersion(){
    }

    public UpdateVersion(long count, String appName, String versionDesc){
        this.count = count;
        this.appName = appName;
        this.versionDesc = versionDesc;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getCurrent() {
        return current;
    }

    public void setCurrent(Long current) {
        this.current = current;
        onChanged();
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getVersionDesc() {
        return versionDesc;
    }

    public void setVersionDesc(String versionDesc) {
        this.versionDesc = versionDesc;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        onChanged();
    }
}
