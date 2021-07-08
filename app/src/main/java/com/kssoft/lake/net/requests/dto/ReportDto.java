package com.kssoft.lake.net.requests.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.kssoft.lake.data.DateChooseItem;
import java.util.Date;

import kiun.com.bvroutine.base.binding.variable.AutoImport;
import kiun.com.bvroutine.base.binding.variable.ObjectVariableSet;
import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.data.QueryBean;
import kiun.com.bvroutine.utils.DateUtil;
import kiun.com.bvroutine.utils.MCString;

/**
 * 简报查询条件.
 */
@AutoImport(ObjectVariableSet.class)
public class ReportDto extends PagerBean<Object, ReportDto> implements DateChooseItem {

    private String rcd; //简报代码
    private String stm; //开始时间
    private String etm;//结束时间
    private int rst;//简报状态 0待科长校核，1科长驳回，2科长通过待局长审核，3局长驳回 4完成
    private String rtp;//简报类型 0湖泛巡测，1应急监测

    public ReportDto() {
        setStart(DateUtil.addDay(new Date(), -7));
        setEnd(new Date());
    }

    public String getRcd() {
        return rcd;
    }

    public void setRcd(String rcd) {
        this.rcd = rcd;
    }

    public String getStm() {
        return stm;
    }

    public void setStm(String stm) {
        this.stm = stm;
        this.etm = stm;
        onChanged(false);
    }

    public String getEtm() {
        return etm;
    }

    public void setEtm(String etm) {
        this.etm = etm;
    }

    public int getRst() {
        return rst;
    }

    public void setRst(int rst) {
        this.rst = rst;
    }

    public String getRtp() {
        return rtp;
    }

    public void setRtp(String rtp) {
        this.rtp = rtp;
        onChanged(false);
    }

    @Override
    public void setStart(Date date) {
        stm = MCString.formatDate("yyyy-MM-dd", date);
        onChanged(false);
    }

    @Override
    @JSONField(serialize = false)
    public Date getStart() {
        return MCString.dateByFormat(stm, "yyyy-MM-dd");
    }

    @Override
    public void setEnd(Date date) {
        etm = MCString.formatDate("yyyy-MM-dd", date);
        onChanged(false);
    }

    @Override
    @JSONField(serialize = false)
    public Date getEnd() {
        return MCString.dateByFormat(etm, "yyyy-MM-dd");
    }
}
