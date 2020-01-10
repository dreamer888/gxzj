package com.dlc.modules.api.entity;

import java.io.Serializable;
import java.util.Date;

public class DeviceConsumeTotal implements Serializable {
    private Long id;

    private String deviceNo;

    private String deviceImei;

    private Integer freePaperTotal;

    private Integer payPaperTotal;

    private Integer gzhLikeTotal;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public DeviceConsumeTotal(Long id, String deviceNo, String deviceImei, Integer freePaperTotal, Integer payPaperTotal, Integer gzhLikeTotal, Date createTime) {
        this.id = id;
        this.deviceNo = deviceNo;
        this.deviceImei = deviceImei;
        this.freePaperTotal = freePaperTotal;
        this.payPaperTotal = payPaperTotal;
        this.gzhLikeTotal = gzhLikeTotal;
        this.createTime = createTime;
    }

    public DeviceConsumeTotal(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public DeviceConsumeTotal(String deviceNo,String deviceImei) {
        this.deviceNo = deviceNo;
        this.deviceImei = deviceImei;
    }
    public DeviceConsumeTotal() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo == null ? null : deviceNo.trim();
    }

    public String getDeviceImei() {
        return deviceImei;
    }

    public void setDeviceImei(String deviceImei) {
        this.deviceImei = deviceImei == null ? null : deviceImei.trim();
    }

    public Integer getFreePaperTotal() {
        return freePaperTotal;
    }

    public void setFreePaperTotal(Integer freePaperTotal) {
        this.freePaperTotal = freePaperTotal;
    }

    public Integer getPayPaperTotal() {
        return payPaperTotal;
    }

    public void setPayPaperTotal(Integer payPaperTotal) {
        this.payPaperTotal = payPaperTotal;
    }

    public Integer getGzhLikeTotal() {
        return gzhLikeTotal;
    }

    public void setGzhLikeTotal(Integer gzhLikeTotal) {
        this.gzhLikeTotal = gzhLikeTotal;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", deviceNo=").append(deviceNo);
        sb.append(", deviceImei=").append(deviceImei);
        sb.append(", freePaperTotal=").append(freePaperTotal);
        sb.append(", payPaperTotal=").append(payPaperTotal);
        sb.append(", gzhLikeTotal=").append(gzhLikeTotal);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}