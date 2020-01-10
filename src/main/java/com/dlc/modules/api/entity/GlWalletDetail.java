package com.dlc.modules.api.entity;

import java.io.Serializable;
import java.util.Date;

public class GlWalletDetail implements Serializable {
    private Long id;

    private Integer type;

    private Integer money;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public GlWalletDetail(Long id, Integer type, Integer money, Date createTime) {
        this.id = id;
        this.type = type;
        this.money = money;
        this.createTime = createTime;
    }

    public GlWalletDetail() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
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
        sb.append(", type=").append(type);
        sb.append(", money=").append(money);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}