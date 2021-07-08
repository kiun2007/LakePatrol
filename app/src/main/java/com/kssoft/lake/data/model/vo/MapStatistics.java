package com.kssoft.lake.data.model.vo;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class MapStatistics {

    private int[] statistics = new int[MapStateVo.legendMipMaps.length];

    public Drawable getIcon(int index, Context context){
        return context.getResources().getDrawable(MapStateVo.legendMipMaps[index]);
    }

    public String getTitle(int index){
        String headTitle = new String[]{"已巡","未巡","警戒"}[index % 3];
        return String.format("%s\n(%d)", headTitle, statistics[index]);
    }

    public void add(int index){
        statistics[index] ++;
    }
}
