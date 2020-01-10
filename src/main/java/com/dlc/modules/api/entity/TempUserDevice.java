package com.dlc.modules.api.entity;

import java.io.Serializable;

public class TempUserDevice implements Serializable {
    private Long id;

    private String openid;

    private String imei;

    private String deviceNo;

    private String appid;

    private String freeCode;

    private String glOpenid;

    private static final long serialVersionUID = 1L;

    public TempUserDevice(Long id, String openid, String imei, String deviceNo, String appid, String freeCode, String glOpenid) {
        this.id = id;
        this.openid = openid;
        this.imei = imei;
        this.deviceNo = deviceNo;
        this.appid = appid;
        this.freeCode = freeCode;
        this.glOpenid = glOpenid;
    }

    public TempUserDevice() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei == null ? null : imei.trim();
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo == null ? null : deviceNo.trim();
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public String getFreeCode() {
        return freeCode;
    }

    public void setFreeCode(String freeCode) {
        this.freeCode = freeCode == null ? null : freeCode.trim();
    }

    public String getGlOpenid() {
        return glOpenid;
    }

    public void setGlOpenid(String glOpenid) {
        this.glOpenid = glOpenid == null ? null : glOpenid.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", openid=").append(openid);
        sb.append(", imei=").append(imei);
        sb.append(", deviceNo=").append(deviceNo);
        sb.append(", appid=").append(appid);
        sb.append(", freeCode=").append(freeCode);
        sb.append(", glOpenid=").append(glOpenid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}