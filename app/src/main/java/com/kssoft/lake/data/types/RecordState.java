package com.kssoft.lake.data.types;

import com.kssoft.lake.R;

/**
 * 巡查记录状态.
 */
public enum RecordState {

    /**
     * 进行中(正在巡查).
     */
    Starting(R.mipmap.ic_map_btn_background),

    /**
     * 暂停巡查.
     */
    Paused(R.drawable.shape_lake_map_start_button),

    /**
     * 停止巡查.校核中
     */
    Stopped(R.drawable.shape_lake_map_start_button),

    /**
     * 被拒绝.
     */
    Reject(0),

    /**
     * 完成
     */
    Complete(0);

    int bgRes;

    RecordState(int bg){
        bgRes = bg;
    }

    public static RecordState getValueOfIndex(int index){
        return RecordState.values()[index];
    }

    public int getBgRes() {
        return bgRes;
    }
}