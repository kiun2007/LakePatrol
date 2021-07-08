package com.kssoft.lake.net.responses.vo;

public class XcReportP {

    //简报代码
    private String rcd;
    //人员代码
    private String pcd;
    //人员名称
    private String pnm;
    //校核审核标识 0校核，1审核
    private String tp = "1";

    public String getRcd() {
        return rcd;
    }

    public void setRcd(String rcd) {
        this.rcd = rcd;
    }

    public String getPcd() {
        return pcd;
    }

    public void setPcd(String pcd) {
        this.pcd = pcd;
    }

    public String getPnm() {
        return pnm;
    }

    public void setPnm(String pnm) {
        this.pnm = pnm;
    }

    public String getTp() {
        return tp;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }
}
