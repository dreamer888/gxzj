package com.dlc.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 公众号管理表
 */
public class OfficialAccountsEntity implements Serializable {
    //ID
    private Long id;
    //代理商ID
    private Long agentId;
    //公众号名称
    private String name;
    //AppId
    private String appId;
    //appSecret
    private String appSecret;
    //免费领取代码
    private String freeCode;
    //微信号
    private String wxCount;
    //原价，单位分
    private int originalPrice;
    //分成单价，单位分
    private int deductPrice;
    //每日关注上限
    private int upperLimit;
    //公众号log地址
    private String imgUrl;
    //设备编码
    private String deviceNo;
    //设备id
    private Long deviceId;
    //状态1.上架2.下架
    private Integer status;
    //创建时间
    private Date createTime;

    private String token;

    private String phone;
    //服务器接入url
    private String serverURL;

    //省
    private String province;

    //市
    private String city;

    //区
    private String area;

    //公众号投放区域
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    //代理商
    private String agentName;

    public Long getId() {
        return id;
    }

    public Long getAgentId() {
        return agentId;
    }

    public String getName() {
        return name;
    }



    public String getAppSecret() {
        return appSecret;
    }

    public String getFreeCode() {
        return freeCode;
    }

    public String getWxCount() {
        return wxCount;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public int getDeductPrice() {
        return deductPrice;
    }

    public int getUpperLimit() {
        return upperLimit;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public Integer getStatus() {
        return status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public void setFreeCode(String freeCode) {
        this.freeCode = freeCode;
    }

    public void setWxCount(String wxCount) {
        this.wxCount = wxCount;
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice = originalPrice;
    }

    public void setDeductPrice(int deductPrice) {
        this.deductPrice = deductPrice;
    }

    public void setUpperLimit(int upperLimit) {
        this.upperLimit = upperLimit;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
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

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }
}
