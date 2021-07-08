package com.kssoft.lake.data;

import android.location.Location;

import com.amap.api.location.AMapLocation;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.kssoft.lake.utils.LatLngUtil;

import java.util.HashMap;
import java.util.Map;

import kiun.com.bvroutine.interfaces.JSON;
import kiun.com.bvroutine.utils.LocationUtils;

public class LastLocation implements JSON {

    private double lat;

    private double lng;

    private String address;

    public LastLocation(){
    }

    public LastLocation(double lat, double lng, String address) {
        this.lat = lat;
        this.lng = lng;
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Location toLocation(){
        return LocationUtils.addLocation(lat, lng);
    }

    public Point toPoint(){
        return new Point(lng, lat, SpatialReferences.getWgs84());
    }

    public Map<String, Object> toMap(String latName, String lngName){
        Map<String, Object> map = new HashMap<>();
        map.put(latName, lat);
        map.put(lngName, lng);
        return map;
    }

    public LastLocation convert84(){
        double[] lngLat = LatLngUtil.gcj02ToWGS84(lng, lat);
        lng = lngLat[0];
        lat = lngLat[1];
        return this;
    }
}
