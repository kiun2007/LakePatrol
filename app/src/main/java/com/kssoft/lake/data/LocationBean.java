package com.kssoft.lake.data;


public interface LocationBean {

    /**
     * 获取纬度.
     * @return
     */
    double latitude();

    /**
     * 获取经度.
     * @return
     */
    double longitude();

    /**
     * 设置纬度
     * @param lat
     */
    void latitude(double lat);

    /**
     * 设置经度
     * @param lng
     */
    void longitude(double lng);
}
