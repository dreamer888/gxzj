package com.dlc.modules.api.entity;

import java.io.Serializable;
import java.util.Date;

public class PublicFans implements Serializable {
    private Long id;

    private String openId;

    private String appId;

    private Long userId;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public PublicFans(Long id, String openId, String appId, Long userId, Date createTime) {
        this.id = id;
        this.openId = openId;
        this.appId = appId;
        this.userId = userId;
        this.createTime = createTime;
    }

    public PublicFans() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
        sb.append(", openId=").append(openId);
        sb.append(", appId=").append(appId);
        sb.append(", userId=").append(userId);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}