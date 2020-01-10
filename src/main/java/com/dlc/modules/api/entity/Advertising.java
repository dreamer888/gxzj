package com.dlc.modules.api.entity;

import java.io.Serializable;
import java.util.Date;

public class Advertising implements Serializable {
    private Long id;

    private Long userId;

    private Byte type;

    private String city;

    private Integer sex;

    private String age;

    private Integer moneyLevel;

    private Integer price;

    private String name;

    private Date startTime;

    private Date endTime;

    private String imgUrl;

    private Integer status;

    private Date auditFailTime;

    private String auditFailRemark;

    private Date createTime;

    private String link;
    //cpm使用过的次数
    private Integer cpmUseNum;

    private static final long serialVersionUID = 1L;

    public Advertising(Long id, Long userId, Byte type, String city, Integer sex, String age, Integer moneyLevel, Integer price, String name, Date startTime, Date endTime, String imgUrl, Integer status, Date auditFailTime, String auditFailRemark, Date createTime, String link, Integer cpmUseNum) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.city = city;
        this.sex = sex;
        this.age = age;
        this.moneyLevel = moneyLevel;
        this.price = price;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.imgUrl = imgUrl;
        this.status = status;
        this.auditFailTime = auditFailTime;
        this.auditFailRemark = auditFailRemark;
        this.createTime = createTime;
        this.link = link;
        this.cpmUseNum = cpmUseNum;
    }

    public Advertising() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age == null ? null : age.trim();
    }

    public Integer getMoneyLevel() {
        return moneyLevel;
    }

    public void setMoneyLevel(Integer moneyLevel) {
        this.moneyLevel = moneyLevel;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link == null ? null : link.trim();
    }

    public Integer getCpmUseNum() {
        return cpmUseNum;
    }

    public void setCpmUseNum(Integer cpmUseNum) {
        this.cpmUseNum = cpmUseNum;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", type=").append(type);
        sb.append(", city=").append(city);
        sb.append(", sex=").append(sex);
        sb.append(", age=").append(age);
        sb.append(", moneyLevel=").append(moneyLevel);
        sb.append(", price=").append(price);
        sb.append(", name=").append(name);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", imgUrl=").append(imgUrl);
        sb.append(", status=").append(status);
        sb.append(", auditFailTime=").append(auditFailTime);
        sb.append(", auditFailRemark=").append(auditFailRemark);
        sb.append(", createTime=").append(createTime);
        sb.append(", link=").append(link);
        sb.append(",cpmUseNum=").append(cpmUseNum);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}