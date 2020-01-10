package com.dlc.modules.api.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Address implements Serializable {
    private Long addressId;

    private String name;

    private String phone;

    private String province;

    private String addr;

    private Byte isDefault;

    private Long userId;

    private Byte status;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Address(Long addressId, String name, String phone, String province, String addr, Byte isDefault, Long userId, Byte status, Date createTime) {
        this.addressId = addressId;
        this.name = name;
        this.phone = phone;
        this.province = province;
        this.addr = addr;
        this.isDefault = isDefault;
        this.userId = userId;
        this.status = status;
        this.createTime = createTime;
    }

    public Address() {
        super();
    }

    public Long getAddressId() {
        return addressId;
    }

    public Long setAddressId(Long addressId) {
        this.addressId = addressId;
        return addressId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr == null ? null : addr.trim();
    }

    public Byte getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Byte isDefault) {
        this.isDefault = isDefault;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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
        sb.append(", addressId=").append(addressId);
        sb.append(", name=").append(name);
        sb.append(", phone=").append(phone);
        sb.append(", province=").append(province);
        sb.append(", addr=").append(addr);
        sb.append(", isDefault=").append(isDefault);
        sb.append(", userId=").append(userId);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}