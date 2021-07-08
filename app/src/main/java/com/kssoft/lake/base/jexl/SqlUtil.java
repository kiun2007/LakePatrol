package com.kssoft.lake.base.jexl;

import android.location.Location;

import com.province.libcacheline.data.beans.SettingUnit;

import java.util.Map;

import kiun.com.bvroutine.utils.LocationUtils;

public class SqlUtil {

    private static final String LocalSQL = "SELECT abs((%s-%s)*111000+(%s-%s)*111000) AS xy, * FROM %s WHERE xy<%s";

    public String distanceSql(SettingUnit unit, Map<String, Object> param, String latName, String lngName, String limit) {
        return String.format(LocalSQL, param.get(latName), latName, param.get(lngName), lngName, unit.Name(), limit);
    }


    public double distance(Double descLat, Double descLng, Double srcLat, Double srcLng){

        Location desc = LocationUtils.addLocation(descLat, descLng);
        Location src = LocationUtils.addLocation(srcLat, srcLng);
        return src.distanceTo(desc);
    }
}
