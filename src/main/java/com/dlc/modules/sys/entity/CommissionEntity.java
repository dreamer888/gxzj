package com.dlc.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 佣金明细表
 */
public class CommissionEntity implements Serializable {
    //佣金表ID
    private Long id;
    //代理商id
    private Long agentId;
    //金额
    private int money;
    //创建时间
    private Date createTime;

    //用户类型
    private Integer type;
    //姓名
    private String name;
    //电话
    private String phone;
    //用户角色
    private Integer role;

    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public Integer getRole() {
        return role;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRole(Integer role) {
        this.role = role;
    }


    public Long getId() {
        return id;
    }

    public Long getAgentId() {
        return agentId;
    }

    public int getMoney() {
        return money;
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

    public void setMoney(int money) {
        this.money = money;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
