package com.kssoft.lake.net.requests.dto;

import com.kssoft.lake.net.responses.vo.TreeVo;
import com.kssoft.lake.net.responses.vo.XcReportP;

import java.util.List;

import kiun.com.bvroutine.utils.ListUtil;

/**
 * 校核审核时参数类
 */
public class RepCkDto {

    private String rcd;//简报代码
    private int rst;//简报状态 0待科长校核，1科长驳回，2科长通过待局长审核，3局长驳回 4完成
    private String nt;//驳回理由
    private List<XcReportP> xcReportP;//校核审核人员

    public RepCkDto(String rcd) {
        this.rcd = rcd;
    }

    /**
     * 科长通过并设置审核人员列表
     * @param list
     */
    public RepCkDto passAddPerson(List<TreeVo> list){
        rst = 2;
        xcReportP = ListUtil.toList(list, item->new XcReportP(){{
            setRcd(rcd);
            setPcd(item.getId());
            setPnm(item.getLabel());
        }});
        return this;
    }

    public RepCkDto release(){
        rst = 0;
        return this;
    }

    /**
     * 局长通过并完成.
     * @return
     */
    public RepCkDto passAndSuccess(){
        rst = 4;
        return this;
    }

    /**
     * 再次发送审批请求.
     * @param rst 审批状态.
     * @return
     */
    public RepCkDto againSend(String rst){
        if ("1".equals(rst)){
            this.rst = 0;
        }else if ("3".equals(rst)){
            this.rst = 2;
        }
        return this;
    }

    public RepCkDto refuse(String rst){
        if ("0".equals(rst)){
            this.rst = 1;
        }else if ("2".equals(rst)){
            this.rst = 3;
        }
        return this;
    }

    public String getRcd() {
        return rcd;
    }

    public void setRcd(String rcd) {
        this.rcd = rcd;
    }

    public int getRst() {
        return rst;
    }

    public void setRst(int rst) {
        this.rst = rst;
    }

    public String getNt() {
        return nt;
    }

    public void setNt(String nt) {
        this.nt = nt;
    }

    public List<XcReportP> getXcReportP() {
        return xcReportP;
    }

    public void setXcReportP(List<XcReportP> xcReportP) {
        this.xcReportP = xcReportP;
    }
}
