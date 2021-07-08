package com.province.libcacheline.data.beans;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.province.libcacheline.JSONForeach;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class XJSONObject extends HashMap<String, Object> {

    public XJSONObject(){
        super();
    }

    public final<E> void foreach(JSONForeach<E> call){
        foreach(false, call);
    }

    public final<E> void foreach(boolean notnull, JSONForeach<E> call){
        for (String key : keySet()) {
            Object value = this.get(key);
            if (notnull && value == null){
                continue;
            }
            if (!call.itemCall(key, (E) value)) break;
        }
    }

    public static XJSONObject create(String json){

        if(!TextUtils.isEmpty(json)){
            XJSONObject xjsonObject = new XJSONObject();
            Map<String, Object> jsonMap = (Map<String, Object>) JSON.parse(json);
            xjsonObject.putAll(jsonMap);
            return xjsonObject;
        }
        return null;
    }
}
