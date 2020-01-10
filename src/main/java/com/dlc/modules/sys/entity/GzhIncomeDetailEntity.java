package com.dlc.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 公众号收益明细表
 */
public class GzhIncomeDetailEntity implements Serializable {
    //ID
    private Long id;
    //公众号ID
    private String appId;
    //公众号名称
    private String gzhName;
    //关注次数
    private int likeNum;
    //设备imei
    private String deviceImei;
    //创建时间
    private Date createTime;
    //每天每个公众号的总收益
    private int gzhcount;


    //创建者名称
    private String aName;
    //原价，单位分
    private int originalPrice;
    //分成单价，单位分
    private int deductPrice;

    private String oName;

    public String getoName() {
        return oName;
    }

    public void setoName(String oName) {
        this.oName = oName;
    }

    public String getaName() {
        return aName;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice = originalPrice;
    }

    public int getDeductPrice() {
        return deductPrice;
    }

    public void setDeductPrice(int deductPrice) {
        this.deductPrice = deductPrice;
    }

    public int getGzhcount() {
        return gzhcount;
    }

    public void setGzhcount(int gzhcount) {
        this.gzhcount = gzhcount;
    }

    public Long getId() {
        return id;
    }

    public String getAppId() {
        return appId;
    }

    public String getGzhName() {
        return gzhName;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public String getDeviceImei() {
        return deviceImei;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setGzhName(String gzhName) {
        this.gzhName = gzhName;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public void setDeviceImei(String deviceImei) {
        this.deviceImei = deviceImei;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
