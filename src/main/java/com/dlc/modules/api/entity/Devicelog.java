package com.dlc.modules.api.entity;

import java.io.Serializable;
import java.util.Date;

public class Devicelog implements Serializable {
    private Long logId;

    private String deviceNo;

    private String addressId;

    private Long maintainerId;

    private String purpose;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Devicelog(Long logId, String deviceNo, String addressId, Long maintainerId, String purpose, Date createTime) {
        this.logId = logId;
        this.deviceNo = deviceNo;
        this.addressId = addressId;
        this.maintainerId = maintainerId;
        this.purpose = purpose;
        this.createTime = createTime;
    }

    public Devicelog() {
        super();
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo == null ? null : deviceNo.trim();
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public Long getMaintainerId() {
        return maintainerId;
    }

    public void setMaintainerId(Long maintainerId) {
        this.maintainerId = maintainerId;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose == null ? null : purpose.trim();
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
        sb.append(", logId=").append(logId);
        sb.append(", deviceNo=").append(deviceNo);
        sb.append(", addressId=").append(addressId);
        sb.append(", maintainerId=").append(maintainerId);
        sb.append(", purpose=").append(purpose);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}