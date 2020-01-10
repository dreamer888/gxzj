package com.dlc.modules.api.entity;

import java.io.Serializable;
import java.util.Date;

public class OfficialAccounts implements Serializable {
    private Long id;

    private String name;

    private String appId;

    private String appSecret;

    private String freeCode;

    private String wxCount;

    private Integer originalPrice;

    private Integer deductPrice;

    private Integer upperLimit;

    private String imgUrl;

    private Long deviceId;

    private Integer status;

    private Date createTime;

    private Long agentId;

    private String deviceNo;
    //供应商账号
    private String phone;

    private String token;
    //服务器接入url
    private String serverURL;

    private static final long serialVersionUID = 1L;

    public OfficialAccounts() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret == null ? null : appSecret.trim();
    }

    public String getFreeCode() {
        return freeCode;
    }

    public void setFreeCode(String freeCode) {
        this.freeCode = freeCode == null ? null : freeCode.trim();
    }

    public String getWxCount() {
        return wxCount;
    }

    public void setWxCount(String wxCount) {
        this.wxCount = wxCount == null ? null : wxCount.trim();
    }

    public Integer getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Integer originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Integer getDeductPrice() {
        return deductPrice;
    }

    public void setDeductPrice(Integer deductPrice) {
        this.deductPrice = deductPrice;
    }

    public Integer getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(Integer upperLimit) {
        this.upperLimit = upperLimit;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", appId=").append(appId);
        sb.append(", appSecret=").append(appSecret);
        sb.append(", freeCode=").append(freeCode);
        sb.append(", wxCount=").append(wxCount);
        sb.append(", originalPrice=").append(originalPrice);
        sb.append(", deductPrice=").append(deductPrice);
        sb.append(", upperLimit=").append(upperLimit);
        sb.append(", imgUrl=").append(imgUrl);
        sb.append(", deviceId=").append(deviceId);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getServerURL() {
        return serverURL;
    }

    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }
}