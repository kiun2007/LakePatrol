package com.kssoft.lake.base.jexl;

import android.annotation.SuppressLint;

import com.kssoft.lake.data.SamplingBase;
import com.kssoft.lake.net.responses.vo.XcTaskPro;

import java.util.HashMap;
import java.util.Map;
import kiun.com.bvroutine.utils.ListUtil;
import kiun.com.bvroutine.utils.ObjectUtil;

public class NumberChecker {

    public boolean equalFieldCheck(Object first, Object second, String fieldName){
        return false;
    }

    /**
     * 检查字段数值的正确性.
     * @param first
     * @param limitValue
     * @param fieldName
     * @return
     */
    public Integer checkES(SamplingBase first, Object limitValue, String fieldName){

        if (first == null){
            return -1;
        }

        Number valueNumber = ObjectUtil.getValue(first, fieldName, Number.class);

        Map<String, XcTaskPro> sourceMap = new HashMap<>();
        ListUtil.map(first.getSource(), v -> sourceMap.put(v.getEnname().toLowerCase(), v));

        XcTaskPro xcTaskPro = sourceMap.get(fieldName);
        if (xcTaskPro != null && !xcTaskPro.isCheckPass()){
            return 2;
        }

        //非数值和不限制,直接跳过验证.
        if (limitValue == null && valueNumber == null){
            return 0;
        }

        Number maxNumber = ObjectUtil.getValue(limitValue, fieldName + "e", Number.class);
        Number minNumber = ObjectUtil.getValue(limitValue, fieldName + "s", Number.class);

        if (maxNumber == null){
            maxNumber = Double.MAX_VALUE;
        }

        if (minNumber == null){
            minNumber = Double.MIN_VALUE;
        }

        if ((maxNumber.doubleValue() == 0 && minNumber.doubleValue() == 0) ||
                (valueNumber.doubleValue() >= minNumber.doubleValue() && valueNumber.doubleValue() <= maxNumber.doubleValue())){
            return 0;
        }
        return 1;
    }

    @SuppressLint("DefaultLocale")
    public String descES(Object limitValue, String fieldName, Integer extraResult){

        if (extraResult == -1){
            return "未知错误";
        }

        if (extraResult == 2){
            return "两次值填报不一致,请检查";
        }

        Number maxNumber = ObjectUtil.getValue(limitValue, fieldName + "e", Number.class);
        Number minNumber = ObjectUtil.getValue(limitValue, fieldName + "s", Number.class);
        if (maxNumber == null || minNumber == null){
            return "输入值不在范围内";
        }
        return String.format("应在(%.1f, %.1f)之间", minNumber.doubleValue(), maxNumber.doubleValue());
    }
}
