package com.kssoft.lake.base.presenter;

import com.kssoft.lake.data.model.vo.DatVersion;

import java.io.IOException;

import kiun.com.bvroutine.interfaces.callers.SetCaller;

public interface VersionUpdate {

    DatVersion checkVersion(String packageName);

    DatVersion checkVersion();

    DatVersion getVersion();

    void share();

    boolean download(SetCaller<Long> callBack) throws IOException;

    boolean installApp();

    /**
     * 检查、更新、启动、
     * @return
     */
    boolean compound(String packageName);

    /**
     * 检查、更新、本应用.
     * @return
     */
    boolean compound();
}