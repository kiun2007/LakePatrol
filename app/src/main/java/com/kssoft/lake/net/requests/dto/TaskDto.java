package com.kssoft.lake.net.requests.dto;

import com.kssoft.lake.data.model.XcTaskR;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import kiun.com.bvroutine.base.EventBean;
import kiun.com.bvroutine.utils.DateUtil;
import kiun.com.bvroutine.utils.ListUtil;
import kiun.com.bvroutine.utils.MCString;

/**
 * 巡测任务 参数类
 */
public class TaskDto extends EventBean {

    private Date choose = new Date();

    private Integer index = 0;

    public TaskDto(Integer index) {
        this.index = index;
    }

    public TaskDto(){
    }

    private List<XcTaskR> yesterdayTaskList = new LinkedList<>();
    private List<XcTaskR> todayTaskList = new LinkedList<>();

    public Date getChoose() {
        return choose;
    }

    public void setChoose(Date choose) {
        this.choose = choose;
        onChanged(false);
    }

    public String getFormatDate(){
        return MCString.formatDate("yyyy-MM-dd", choose);
    }

    public String getMonth(){
        return MCString.formatDate("MMM", choose);
    }

    public String getYear(){
        return MCString.formatDate("yyyy", choose);
    }

    public String getXctp(){
        if (index != null){
            return String.valueOf(index);
        }
        return null;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
        yesterdayTaskList.clear();
        todayTaskList.clear();
        onChanged(false);
    }

    public void setTaskList(List<XcTaskR> taskList) {

        Map<Date, List<XcTaskR>> listMap = new HashMap<>();
        for (XcTaskR xcTaskR : taskList){
            List<XcTaskR> xcTaskRList = listMap.get(xcTaskR.getTm());
            if (xcTaskRList == null){
                xcTaskRList = new LinkedList<>();
                listMap.put(xcTaskR.getTm(), xcTaskRList);
            }

            xcTaskRList.add(xcTaskR);
        }

        String today = MCString.formatDate("yyyy-MM-dd", new Date());
        if (listMap.size() == 1){
            for (Date date : listMap.keySet()){
                if (MCString.formatDate("yyyy-MM-dd", date).equals(today)){
                    todayTaskList = listMap.get(date);
                }else{
                    yesterdayTaskList = listMap.get(date);
                }
            }
        }else if (listMap.size() == 2){
            Date[] allDate = new Date[2];
            listMap.keySet().toArray(allDate);

            if (allDate[0].before(allDate[1])){
                yesterdayTaskList = listMap.get(allDate[0]);
                todayTaskList = listMap.get(allDate[1]);
            }else{
                yesterdayTaskList = listMap.get(allDate[1]);
                todayTaskList = listMap.get(allDate[0]);
            }
        }
        onChanged();
    }


    public List<XcTaskR> getTodayTaskList() {
        return todayTaskList;
    }

    private String[] getTitles(String title, int lastCount, int nowCount){

        String increase = "";
        String incColor = "#999999";
        if (lastCount > 0 && nowCount > 0){
            float inc = (float) (nowCount - lastCount) / lastCount;
            increase = String.format("%.1f%%", inc*100);
            if(inc != 0){
                incColor = inc > 0 ? "#FF6600" : "#00F4CC";
            }
        }
        return new String[]{title, String.valueOf(nowCount), increase, incColor};
    }

    private int countBySite(List<XcTaskR> xcTaskRList, int type){
        AtomicInteger count = new AtomicInteger();
        ListUtil.map(xcTaskRList, item->{
            count.addAndGet(item.patrolCount(type));
        });
        return count.get();
    }

    private String[] getCountByState(int type){

        String[] titles = new String[]{"巡查计划","已巡查计划","未巡查计划"};
        return getTitles(titles[type], countBySite(yesterdayTaskList, type), countBySite(todayTaskList, type));
    }

    /**
     * 计划总数以及上升.
     * @return
     */
    public String[] getCountTitles(){
        return getCountByState(0);
    }

    public String[] getCountCompleted(){
        return getCountByState(1);
    }

    public String[] getCountUnaccomplished(){
        return getCountByState(2);
    }

    public String[] getCountTask(){
        return getTitles("总任务数", yesterdayTaskList.size(), todayTaskList.size());
    }
}