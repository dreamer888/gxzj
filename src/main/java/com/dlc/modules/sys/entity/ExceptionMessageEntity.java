package com.dlc.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;

public class ExceptionMessageEntity implements Serializable {
    private Long messageId;

    private String deviceNo;

    private String addressId;

    private String context;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public ExceptionMessageEntity(Long messageId, String deviceNo, String addressId, String context, Date createTime) {
        this.messageId = messageId;
        this.deviceNo = deviceNo;
        this.addressId = addressId;
        this.context = context;
        this.createTime = createTime;
    }

    public ExceptionMessageEntity() {
        super();
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
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

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
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
        sb.append(", messageId=").append(messageId);
        sb.append(", deviceNo=").append(deviceNo);
        sb.append(", addressId=").append(addressId);
        sb.append(", context=").append(context);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}