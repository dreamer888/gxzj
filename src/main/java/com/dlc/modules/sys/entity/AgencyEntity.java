package com.dlc.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;

public class AgencyEntity implements Serializable{

    //代理管理id
    private Long id;
    //父ID
    private Long parentId;
    //用户类型
    private Integer type;
    //姓名
    private String name;
    //用户头像
    private String headImgUrl;
    //电话
    private String phone;
    //密码
    private String password;
    //钱包
    private int wallet;
    //用户状态
    private Integer status;
    //用户角色
    private Integer role;
    //删除状态
    private Integer deleteStatus;
    //代理城市
    private String city;
    //分佣值
    private int commissionValue;
    //佣金类型
    private Integer commissionType;
    //创建时间
    private Date createTime;

    public int getCommissionValue() {
        return commissionValue;
    }

    public void setCommissionValue(int commissionValue) {
        this.commissionValue = commissionValue;
    }

    public Integer getCommissionType() {
        return commissionType;
    }

    public void setCommissionType(Integer commissionType) {
        this.commissionType = commissionType;
    }

    public Long getId() {
        return id;
    }

    public Long getParentId() {
        return parentId;
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public int getWallet() {
        return wallet;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getRole() {
        return role;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public String getCity() {
        return city;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
