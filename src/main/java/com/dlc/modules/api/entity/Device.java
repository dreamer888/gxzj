package com.dlc.modules.api.entity;

import java.io.Serializable;
import java.util.Date;

public class Device implements Serializable {
    private Long deviceId;

    private String deviceNo;

    private String imei;

    private String addressDetail;

    private Long addressId;

    private Long proxyId;

    private Integer status;

    private Integer inventory;

    private String goodsName;

    private Integer goodsPrice;

    private Integer price;

    private Date createTime;

    private String goodsImgUrl;

    private Date onLineTime;

    private static final long serialVersionUID = 1L;

    public Device(Long deviceId, String deviceNo, String imei, String addressDetail, Long addressId, Long proxyId, Integer status, Integer inventory, String goodsName, Integer goodsPrice, Integer price, Date createTime, String goodsImgUrl) {
        this.deviceId = deviceId;
        this.deviceNo = deviceNo;
        this.imei = imei;
        this.addressDetail = addressDetail;
        this.addressId = addressId;
        this.proxyId = proxyId;
        this.status = status;
        this.inventory = inventory;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.price = price;
        this.createTime = createTime;
        this.goodsImgUrl = goodsImgUrl;
    }

    public Device(String deviceNo, String imei) {
        this.deviceNo = deviceNo;
        this.imei = imei;
    }

    public Device() {
        super();
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo == null ? null : deviceNo.trim();
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei == null ? null : imei.trim();
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail == null ? null : addressDetail.trim();
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getProxyId() {
        return proxyId;
    }

    public void setProxyId(Long proxyId) {
        this.proxyId = proxyId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public Integer getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Integer goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getGoodsImgUrl() {
        return goodsImgUrl;
    }

    public void setGoodsImgUrl(String goodsImgUrl) {
        this.goodsImgUrl = goodsImgUrl == null ? null : goodsImgUrl.trim();
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
        sb.append(", deviceId=").append(deviceId);
        sb.append(", deviceNo=").append(deviceNo);
        sb.append(", imei=").append(imei);
        sb.append(", addressDetail=").append(addressDetail);
        sb.append(", addressId=").append(addressId);
        sb.append(", proxyId=").append(proxyId);
        sb.append(", status=").append(status);
        sb.append(", inventory=").append(inventory);
        sb.append(", goodsName=").append(goodsName);
        sb.append(", goodsPrice=").append(goodsPrice);
        sb.append(", price=").append(price);
        sb.append(", createTime=").append(createTime);
        sb.append(", goodsImgUrl=").append(goodsImgUrl);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}