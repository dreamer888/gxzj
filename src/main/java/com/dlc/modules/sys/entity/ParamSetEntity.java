package com.dlc.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;

public class ParamSetEntity implements Serializable {
    //ID
    private int id;
    //参数名称
    private String name;
    //参数值
    private String paramValue;
    //类型1.每周每个用户领取的次数2.提现周期
    private Integer type;
    //创建时间
    private Date createTime;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getParamValue() {
        return paramValue;
    }

    public Integer getType() {
        return type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
