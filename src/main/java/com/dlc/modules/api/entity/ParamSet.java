package com.dlc.modules.api.entity;

import java.io.Serializable;
import java.util.Date;

public class ParamSet implements Serializable {
    private Integer id;

    private String name;

    private String paramValue;

    private Byte type;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public ParamSet(Integer id, String name, String paramValue, Byte type, Date createTime) {
        this.id = id;
        this.name = name;
        this.paramValue = paramValue;
        this.type = type;
        this.createTime = createTime;
    }

    public ParamSet() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue == null ? null : paramValue.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
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
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", paramValue=").append(paramValue);
        sb.append(", type=").append(type);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}