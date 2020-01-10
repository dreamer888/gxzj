package com.dlc.modules.api.entity;

import java.io.Serializable;
import java.util.Date;

public class WalletDetail implements Serializable {
    private Long id;

    private Long userId;

    private Integer type;

    private Integer money;

    private String transactionNumber;

    private String reason;

    private Integer status;

    private Date createTime;

    private Date transactionTime;

    private Date checkedTime;

    private String wxCount;

    private String orderNo;

    private String openId;

    private static final long serialVersionUID = 1L;

    public WalletDetail(Long id, Long userId, Integer type, Integer money, String transactionNumber, String reason, Integer status, Date createTime, String wxCount, String openId) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.money = money;
        this.transactionNumber = transactionNumber;
        this.reason = reason;
        this.status = status;
        this.createTime = createTime;
        this.wxCount = wxCount;
        this.orderNo = orderNo;
        this.openId = openId;
    }

    public WalletDetail() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber == null ? null : transactionNumber.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getWxCount() {
        return wxCount;
    }

    public void setWxCount(String wxCount) {
        this.wxCount = wxCount;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", type=").append(type);
        sb.append(", money=").append(money);
        sb.append(", transactionNumber=").append(transactionNumber);
        sb.append(", reason=").append(reason);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", wxCount=").append(wxCount);
        sb.append(", orderNo=").append(orderNo);
        sb.append(", openId=").append(openId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public Date getCheckedTime() {
        return checkedTime;
    }

    public void setCheckedTime(Date checkedTime) {
        this.checkedTime = checkedTime;
    }
}