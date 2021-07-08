package com.kssoft.lake.base.presenter;

public interface GraphicsPoint {

    double getLat();

    double getLng();

    /**
     * 绘制唯一标识,不允许重复添加.
     * @return
     */
    String key();

    int count();
}
