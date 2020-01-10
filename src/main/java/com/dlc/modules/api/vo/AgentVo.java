package com.dlc.modules.api.vo;

import com.dlc.common.validator.group.AddGroup;
import com.dlc.common.validator.group.LoginGroup;
import com.dlc.common.validator.group.UpdateGroup;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


public class AgentVo implements Serializable {
    private Long id;
    /**父ID*/
    private Long parentId;
    /**父昵称*/
    private String parentName;
    /**用户昵称*/
    private String name;
    /**手机号*/
    private String phone;
    @NotNull(message="密码不能为空",groups = {AddGroup.class,UpdateGroup.class, LoginGroup.class})
    private String password;
    /**性别，用户类型：0 广告主，1 供货主，2 管理员，3 运维人员，4  财务人员*/
    private Integer type;
    /**用户角色 0 普通用户，1省级，2 市级，3 区级，4个人*/
    private Integer role;
    /**状态 用户状态(1 启用 2 禁用)*/
    private Integer status;
    /**微信openId*/
    private String openId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}