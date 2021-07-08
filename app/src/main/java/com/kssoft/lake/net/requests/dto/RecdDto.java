package com.kssoft.lake.net.requests.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.kssoft.lake.data.DateChooseItem;
import java.util.Date;
import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.utils.MCString;

/**
 * 巡测记录查询参数
 */
public class RecdDto extends PagerBean<Void, RecdDto> implements DateChooseItem {

    private String stm;//开始时间
    private String etm;//结束时间
    private String xctp;//巡测类型
    private String rdst;//巡测状态
    private String rdcd;//记录编号
    private String stcd;//测站编码
    private String rdctp;//人员编号

    public RecdDto(){}

    public RecdDto(String rdst) {
        this.rdst = rdst;
    }

    public String getStm() {
        return stm;
    }

    public void setStm(String stm) {
        this.stm = stm;
    }

    public String getEtm() {
        return etm;
    }

    public void setEtm(String etm) {
        this.etm = etm;
    }

    public String getXctp() {
        return xctp;
    }

    public void setXctp(String xctp) {
        this.xctp = xctp;
    }

    public String getRdst() {
        return rdst;
    }

    public void setRdst(String rdst) {
        this.rdst = rdst;
        onChanged(false);
    }

    public String getRdcd() {
        return rdcd;
    }

    public void setRdcd(String rdcd) {
        this.rdcd = rdcd;
    }

    public String getStcd() {
        return stcd;
    }

    public void setStcd(String stcd) {
        this.stcd = stcd;
    }

    public String getRdctp() {
        return rdctp;
    }

    public void setRdctp(String rdctp) {
        this.rdctp = rdctp;
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
