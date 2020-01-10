package com.dlc.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;

public class AdvertisingEntity implements Serializable {
    //广告管理id
    private Long id;
    //广告类型(cpc,cpm)
    private String type;
    //投放城市
    private String city;
    //性别
    private int sex;
    //投放年龄
    private String age;
    //消费水平(1.全部2.高3.中4.低)
    private int moneyLevel;
    //广告价格(分)
    private int price;
    //广告名称
    private String name;
    //广告链接
    private String link;
    //投放时间
    private Date startTime;
    //结束时间
    private Date endTime;
    //广告图
    private String imgUrl;
    //状态(1.已审核2.待投放3.投放中4.已结束)
    private int status;
    //审核时间
    private Date auditFailTime;
    //审核失败原因
    private String auditFailRemark;
    //创建时间
    private Date createTime;

    //用来转换时间戳
    private String strStartDate;
    private String strEndDate;

    private Long aId;
    private String aName;

    public Long getaId() {
        return aId;
    }

    public void setaId(Long aId) {
        this.aId = aId;
    }

    public String getaName() {
        return aName;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }

    public Date getAuditFailTime() {
        return auditFailTime;
    }

    public void setAuditFailTime(Date auditFailTime) {
        this.auditFailTime = auditFailTime;
    }

    public String getAuditFailRemark() {
        return auditFailRemark;
    }

    public void setAuditFailRemark(String auditFailRemark) {
        this.auditFailRemark = auditFailRemark;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getCity() {
        return city;
    }

    public int getSex() {
        return sex;
    }

    public String getAge() {
        return age;
    }

    public int getMoneyLevel() {
        return moneyLevel;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getStatus() {
        return status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setMoneyLevel(int moneyLevel) {
        this.moneyLevel = moneyLevel;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStrStartDate() {
        return strStartDate;
    }

    public void setStrStartDate(String strStartDate) {
        this.strStartDate = strStartDate;
    }

    public String getStrEndDate() {
        return strEndDate;
    }

    public void setStrEndDate(String strEndDate) {
        this.strEndDate = strEndDate;
    }
}
