package com.dlc.modules.api.entity;

import java.io.Serializable;
import java.util.Date;

public class DeviceIncomeTotal implements Serializable {
    private Long id;

    private String deviceNo;

    private String deviceImei;

    private Integer payPaperSum;

    private Integer commissionSum;

    private Integer gzhIncomeSum;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public DeviceIncomeTotal(Long id, String deviceNo, String deviceImei, Integer payPaperSum, Integer commissionSum, Integer gzhIncomeSum, Date createTime) {
        this.id = id;
        this.deviceNo = deviceNo;
        this.deviceImei = deviceImei;
        this.payPaperSum = payPaperSum;
        this.commissionSum = commissionSum;
        this.gzhIncomeSum = gzhIncomeSum;
        this.createTime = createTime;
    }

    public DeviceIncomeTotal(String deviceNo) {
        this.deviceNo = deviceNo;

    }

    public DeviceIncomeTotal(String deviceNo, String deviceImei) {
        this.deviceNo = deviceNo;
        this.deviceImei = deviceImei;
    }

    public DeviceIncomeTotal() {
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

    public Integer getPayPaperSum() {
        return payPaperSum;
    }

    public void setPayPaperSum(Integer payPaperSum) {
        this.payPaperSum = payPaperSum;
    }

    public Integer getCommissionSum() {
        return commissionSum;
    }

    public void setCommissionSum(Integer commissionSum) {
        this.commissionSum = commissionSum;
    }

    public Integer getGzhIncomeSum() {
        return gzhIncomeSum;
    }

    public void setGzhIncomeSum(Integer gzhIncomeSum) {
        this.gzhIncomeSum = gzhIncomeSum;
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
        sb.append(", payPaperSum=").append(payPaperSum);
        sb.append(", commissionSum=").append(commissionSum);
        sb.append(", gzhIncomeSum=").append(gzhIncomeSum);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}