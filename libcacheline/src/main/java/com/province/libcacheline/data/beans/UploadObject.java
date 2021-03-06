package com.province.libcacheline.data.beans;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.province.libcacheline.data.CacheSettingManager;
import com.province.libcacheline.data.SqliteDBHelper;
import com.province.libcacheline.utils.ByteUtil;
import com.province.libcacheline.utils.MCString;
import com.province.libcacheline.utils.MD5Util;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UploadObject {

    public static final String UP_LOAD_DB = "upload.db";

    public static final String UP_LOAD_TABLE = "cache_upload_table";

    public static final String UP_LOAD_Key = "upload_id";

    private String id = null;
    private String action = null;
    private String url = null;
    private String keyValue = null;
    private Object param = null;
    private Map<String, String> header = null;
    private SettingUnit settingUnit;
    private long inTime;
    private long outTime = 0;
    private int tryAgain = 0;
    private int level = 0;
    private String relationId;
    private String errorMsg;
    private SqliteDBHelper dbHelper;

    public UploadObject(String url, SettingUnit settingUnit, Object param, Map<String, String> header, String dummyKey){

        this.settingUnit = settingUnit;

        if (settingUnit.PutFile()){
            param = dummyKey;
        }

        this.param = param;
        this.url = url;
        this.action = settingUnit.Id();
        String key = settingUnit.Key();
        if(settingUnit.getCluster() != null){
            key = settingUnit.getCluster().getKeyName();
        }
        this.keyValue = dummyKey;

        if(param instanceof Map){
            keyValue = !TextUtils.isEmpty((CharSequence) ((Map) param).get(key)) ? (String) ((Map) param).get(key) : dummyKey;
        }
        this.header = header;
        this.id = ByteUtil.bytesToHexString(MD5Util.MD5(action+(keyValue == null ? url : keyValue)));
        inTime = new Date().getTime();
    }

    public UploadObject(String url, SettingUnit settingUnit, Map<String, Object> param, Map<String, String> header){
        this(url, settingUnit, param, header, null);
    }

    public UploadObject(Map<String, Object> objectJSON, CacheSettingManager manager, Map<String, SqliteDBHelper> dbMaps){

        id = (String) objectJSON.get("upload_id");
        action = (String) objectJSON.get("action");
        url = (String) objectJSON.get("url");
        keyValue = (String) objectJSON.get("key_value");

        param = objectJSON.get("param");
        header = (Map<String, String>) JSON.parse((String) objectJSON.get("header"));

        inTime = (long) objectJSON.get("in_time");
        outTime = (long) objectJSON.get("out_time");
        relationId = (String) objectJSON.get("relation_id");
        level = ((Number) objectJSON.get("level")).intValue();
        errorMsg = (String) objectJSON.get("error_msg");

        settingUnit = manager.findUnitById(action);
        dbHelper = dbMaps.get(settingUnit.SaveDB());
    }

    public String getAction() {
        return action;
    }

    public boolean equals(Class clz, String shortName) {
        String actionStr = clz.getName() + "/" + shortName;
        return actionStr.equals(action);
    }


    public String getUrl() {
        return url;
    }

    public Object getParam() {
        return param;
    }

    public Map<String, String> getHeader(){
        return header;
    }

    public void upload(){
        outTime = new Date().getTime();
    }

    public String getUpLoadDb(){
        if (settingUnit != null){
            return settingUnit.SaveDB();
        }
        return null;
    }

    public long getInTime() {
        return inTime;
    }

    public long getOutTime() {
        return outTime;
    }

    public String getKeyValue() {
        return keyValue;
    }

    /**
     * ??????????????????????????????.
     * @return UUID.
     */
    public String getId() {
        return id;
    }

    /**
     * ????????????????????????????????????????????????.
     * @param format ?????????.
     * @return ?????????????????????.
     */
    public String getEditTime(String format){
        return MCString.formatDate(format, new Date(inTime));
    }

    /**
     * ????????????????????????????????????????????????.
     * @return ?????????????????????.
     */
    public String getEditTime(){
        return MCString.formatDate("MM/ss HH:mm:ss", new Date(inTime));
    }

    /**
     * ????????????????????????????????????????????????.
     * @param format ?????????.
     * @return ?????????????????????.
     */
    public String getUploadTime(String format){
        return MCString.formatDate(format, new Date(outTime));
    }

    /**
     * ????????????????????????????????????????????????.
     * @return ?????????????????????.
     */
    public String getUploadTime(){
        return MCString.formatDate("MM/ss HH:mm:ss", new Date(outTime));
    }

    /**
     * ????????????????????????.
     * @return ??????.
     */
    public boolean isUpload(){
        return outTime > 0;
    }

    public void tryUpload(){
        tryAgain ++;
    }

    public int getTryAgain() {
        return tryAgain;
    }

    public String getValue() {
        return keyValue;
    }

    public SettingUnit getSettingUnit(){
        return settingUnit;
    }

    public Map<String, Object> toJSON(){

        Map<String, Object> jsonObject = new HashMap<>();
        jsonObject.put(UP_LOAD_Key, id);
        jsonObject.put("url", url);
        jsonObject.put("param", param instanceof String ? param : JSON.toJSONString(param));
        jsonObject.put("header", header == null ? null : JSON.toJSONString(header));
        jsonObject.put("level", level);
        jsonObject.put("action", action);
        jsonObject.put("in_time", inTime);
        jsonObject.put("out_time", outTime);
        jsonObject.put("key_value", keyValue);
        jsonObject.put("try_again", tryAgain);
        jsonObject.put("error_msg", errorMsg);
        jsonObject.put("relation_id", relationId);
        return jsonObject;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String msg){
        errorMsg = msg;
    }

    public RelationObject getRelation() {
        return new RelationObject(relationId, level);
    }

    public void setRelation(RelationObject relationObject) {
        this.relationId = relationObject.getId();
        this.level = relationObject.getLevel();
    }

    public static boolean putUpload(UploadObject uploadObject, SqliteDBHelper uploadDb){
        Map<String, Object> uploadJSON = uploadObject.toJSON();
        if(uploadJSON == null){
            return false;
        }

        Object ret = uploadDb.createTableBySample(UploadObject.UP_LOAD_TABLE, UploadObject.UP_LOAD_Key, uploadJSON, null);
        if(ret == null){
            return false;
        }

        if(!uploadDb.updateValue(UploadObject.UP_LOAD_TABLE, uploadJSON, true)){
            return false;
        }
        return true;
    }
}
