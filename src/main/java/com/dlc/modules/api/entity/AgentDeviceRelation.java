package com.dlc.modules.api.entity;

import java.io.Serializable;
import java.util.Date;

public class AgentDeviceRelation implements Serializable {
    private Long id;

    private Long agentId;

    private Long opsId;

    private String deviceNo;

    private String deviceImei;

    private Integer inventory;

    private Integer status;

    private String opsPhone;

    private String addr;

    private Integer freePaperTotal;

    private Integer payPaperTotal;

    private Integer gzhLikeTotal;

    private Integer gzhIncomeSum;

    private Integer payPaperSum;

    private Integer commissionSum;

    private Date onLineTime;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public AgentDeviceRelation(Long id, Long agentId, Long opsId, String deviceNo, String deviceImei, Integer inventory, Integer status, String opsPhone, String addr, Integer freePaperTotal, Integer payPaperTotal, Integer gzhLikeTotal, Integer gzhIncomeSum, Integer payPaperSum, Integer commissionSum, Date createTime) {
        this.id = id;
        this.agentId = agentId;
        this.opsId = opsId;
        this.deviceNo = deviceNo;
        this.deviceImei = deviceImei;
        this.inventory = inventory;
        this.status = status;
        this.opsPhone = opsPhone;
        this.addr = addr;
        this.freePaperTotal = freePaperTotal;
        this.payPaperTotal = payPaperTotal;
        this.gzhLikeTotal = gzhLikeTotal;
        this.gzhIncomeSum = gzhIncomeSum;
        this.payPaperSum = payPaperSum;
        this.commissionSum = commissionSum;
        this.createTime = createTime;
    }

    public AgentDeviceRelation(Long agentId,String deviceNo, Integer freePaperTotal, Integer payPaperTotal, Integer gzhLikeTotal, Integer gzhIncomeSum, Integer payPaperSum, Integer commissionSum) {
        this.agentId = agentId;
        this.deviceNo = deviceNo;
        this.freePaperTotal = freePaperTotal;
        this.payPaperTotal = payPaperTotal;
        this.gzhLikeTotal = gzhLikeTotal;
        this.gzhIncomeSum = gzhIncomeSum;
        this.payPaperSum = payPaperSum;
        this.commissionSum = commissionSum;
    }
    public AgentDeviceRelation() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public Long getOpsId() {
        return opsId;
    }

    public void setOpsId(Long opsId) {
        this.opsId = opsId;
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

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOpsPhone() {
        return opsPhone;
    }

    public void setOpsPhone(String opsPhone) {
        this.opsPhone = opsPhone == null ? null : opsPhone.trim();
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr == null ? null : addr.trim();
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

    public Integer getGzhIncomeSum() {
        return gzhIncomeSum;
    }

    public void setGzhIncomeSum(Integer gzhIncomeSum) {
        this.gzhIncomeSum = gzhIncomeSum;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getOnLineTime() {
        return onLineTime;
    }

    public void setOnLineTime(Date onLineTime) {
        this.onLineTime = onLineTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", agentId=").append(agentId);
        sb.append(", opsId=").append(opsId);
        sb.append(", deviceNo=").append(deviceNo);
        sb.append(", deviceImei=").append(deviceImei);
        sb.append(", inventory=").append(inventory);
        sb.append(", status=").append(status);
        sb.append(", opsPhone=").append(opsPhone);
        sb.append(", addr=").append(addr);
        sb.append(", freePaperTotal=").append(freePaperTotal);
        sb.append(", payPaperTotal=").append(payPaperTotal);
        sb.append(", gzhLikeTotal=").append(gzhLikeTotal);
        sb.append(", gzhIncomeSum=").append(gzhIncomeSum);
        sb.append(", payPaperSum=").append(payPaperSum);
        sb.append(", commissionSum=").append(commissionSum);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}