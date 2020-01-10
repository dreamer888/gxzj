package com.dlc.modules.api.entity;

import java.io.Serializable;
import java.util.Date;

public class UserLikeGzh implements Serializable {
    private Long id;

    private String appId;

    private String gzhName;

    private String openId;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public UserLikeGzh(Long id, String appId, String gzhName, String openId, Date createTime) {
        this.id = id;
        this.appId = appId;
        this.gzhName = gzhName;
        this.openId = openId;
        this.createTime = createTime;
    }

    public UserLikeGzh() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public String getGzhName() {
        return gzhName;
    }

    public void setGzhName(String gzhName) {
        this.gzhName = gzhName == null ? null : gzhName.trim();
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
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
        sb.append(", appId=").append(appId);
        sb.append(", gzhName=").append(gzhName);
        sb.append(", openId=").append(openId);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}