package com.kssoft.lake.data.model;

import android.annotation.SuppressLint;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import kiun.com.bvroutine.utils.ListUtil;

/**
 * 巡测任务制定类
 */
public class XcTaskR implements Serializable {

    /**
     * 任务代码
     */
    private String tkcd;

    /**
     * 创建时间
     */
    private Date tm;

    /**
     * 任务类型：0湖泛，1水文，2人工
     */
    private String xctp;

    /**
     * 所属行政
     */
    private String addvcd;

    /**
     * 任务名称
     */
    private String tknm;

    /**
     * 任务对应的人员
     */
    private List<XcTaskP> taskP;

    /**
     * 任务对应的测站
     */
    private List<XcTaskSt> taskSt;

    @JSONField(serialize = false)
    public String getNames(){
        if (!ListUtil.isEmpty(taskP)){
            List<String> names = ListUtil.toList(taskP, item->item.getPnm());
            return ListUtil.join(names, ",");
        }
        return "未分配人员";
    }

    public String getSiteCount(){
        if (!ListUtil.isEmpty(taskSt)){
            return String.valueOf(taskSt.size());
        }
        return "0";
    }

    @SuppressLint("DefaultLocale")
    public String getPercentage(){
        if (ListUtil.isEmpty(taskSt)){
            return "0.0%";
        }
        float p = (float) patrolCount(1)*100 / patrolCount(0);
        return String.format("%.1f%%", p);
    }

    /**
     * 统计数量
     * @param type 0 全部 1已巡 2未巡查.
     * @return
     */
    public int patrolCount(int type){

        if (type == 0){
            return taskSt == null ? 0 : taskSt.size();
        }

        int count = 0;
        if (taskSt != null){
            for (XcTaskSt xcTaskSt : taskSt){
                if (type == 2 ^ "已巡".equals(xcTaskSt.getState())){
                    count++;
                }
            }
        }
        return count;
    }

    public String getTkcd() {
        return tkcd;
    }

    public void setTkcd(String tkcd) {
        this.tkcd = tkcd;
    }

    public Date getTm() {
        return tm;
    }

    public void setTm(Date tm) {
        this.tm = tm;
    }

    public String getXctp() {
        return xctp;
    }

    public void setXctp(String xctp) {
        this.xctp = xctp;
    }

    public String getAddvcd() {
        return addvcd;
    }

    public void setAddvcd(String addvcd) {
        this.addvcd = addvcd;
    }

    public String getTknm() {
        return tknm;
    }

    public void setTknm(String tknm) {
        this.tknm = tknm;
    }

    public List<XcTaskP> getTaskP() {
        return taskP;
    }

    public void setTaskP(List<XcTaskP> taskP) {
        this.taskP = taskP;
    }

    public List<XcTaskSt> getTaskSt() {
        return taskSt;
    }

    public void setTaskSt(List<XcTaskSt> taskSt) {
        this.taskSt = taskSt;
    }
}
