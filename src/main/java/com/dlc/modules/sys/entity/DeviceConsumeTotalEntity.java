package com.dlc.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;

public class DeviceConsumeTotalEntity implements Serializable {
    //ID
    private Long id;
    //设备号
    private String deviceNo;
    //设备IMEI
    private String deviceImei;
    //免费领取纸巾
    private int freePaperTotal;
    //购买商品
    private int payPaperTotal;
    //公众号关注
    private int gzhLikeTotal;
    //创建时间
    private Date createTime;
    //商品收益
    private int payPaperSum;

    //代理商姓名
    private String name;
    private int price;
    private int goodsPrice;
    private  Long deviceId;
    private int sumDevice;

    public int getSumDevice() {
        return sumDevice;
    }

    public void setSumDevice(int sumDevice) {
        this.sumDevice = sumDevice;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public int getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(int goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPayPaperSum() {
        return payPaperSum;
    }

    public void setPayPaperSum(int payPaperSum) {
        this.payPaperSum = payPaperSum;
    }

    public Long getId() {
        return id;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public String getDeviceImei() {
        return deviceImei;
    }

    public int getFreePaperTotal() {
        return freePaperTotal;
    }

    public int getPayPaperTotal() {
        return payPaperTotal;
    }

    public int getGzhLikeTotal() {
        return gzhLikeTotal;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public void setDeviceImei(String deviceImei) {
        this.deviceImei = deviceImei;
    }

    public void setFreePaperTotal(int freePaperTotal) {
        this.freePaperTotal = freePaperTotal;
    }

    public void setPayPaperTotal(int payPaperTotal) {
        this.payPaperTotal = payPaperTotal;
    }

    public void setGzhLikeTotal(int gzhLikeTotal) {
        this.gzhLikeTotal = gzhLikeTotal;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
