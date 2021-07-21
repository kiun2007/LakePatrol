package com.kssoft.lake.data.types;

import android.text.TextUtils;

import com.kssoft.lake.R;
import com.kssoft.lake.data.SamplingBase;
import com.kssoft.lake.data.model.commit.SamplingGeneral;
import com.kssoft.lake.data.model.commit.XcLakeR;
import com.kssoft.lake.data.model.commit.XcRiverR;
import com.kssoft.lake.data.model.commit.XcWqnmispR;
import com.kssoft.lake.data.model.commit.XcWrrbR;

public enum SamplingType {

    /**
     * 错误类型.
     */
    Empty( null, -1, null),

    /**
     * 湖泛
     */
    Lake(XcLakeR.class, R.mipmap.ic_map_title_sort_hufan,"湖泛巡查采样", "0"),

    /**
     * 水文
     */
    Hydrology(SamplingGeneral.class, R.mipmap.ic_map_title_sort_shuiwen,"水文巡查采样","1"),

    /**
     * 人工
     */
    Manual(SamplingGeneral.class, R.mipmap.ic_map_title_sort_rengong,"人工巡测采样","2"),

    /**
     * 应急.
     */
    Urgent(XcWrrbR.class, R.mipmap.ic_map_title_sort_yingji,"应急监测采样","3");

    private Class<? extends SamplingBase> clz;
    private int icon;
    private String title;
    private String type = null;

    SamplingType(Class<? extends SamplingBase> clz, int icon, String title, String type) {
        this.clz = clz;
        this.icon = icon;
        this.title = title;
        this.type = type;
    }

    SamplingType(Class<? extends SamplingBase> clz, int icon, String title) {
        this.clz = clz;
        this.icon = icon;
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public String getType() {
        return type;
    }

    public static SamplingType getType(Class clz){
        if (XcLakeR.class.equals(clz)){
            return Lake;
        }else if (XcRiverR.class.equals(clz)){
            return Hydrology;
        }else if (XcWrrbR.class.equals(clz)){
            return Manual;
        }else if (XcWqnmispR.class.equals(clz)){
            return Urgent;
        }
        return Empty;
    }

    public static SamplingType getType(String value){
        if (!TextUtils.isEmpty(value)){
            switch (value){
                case "0":
                    return Lake;
                case "1":
                    return Hydrology;
                case "2":
                    return Manual;
                case "3":
                    return Urgent;
            }
        }
        return Empty;
    }

    public Class<? extends SamplingBase> getClz() {
        return clz;
    }

    public String getTitle() {
        return title;
    }
}
