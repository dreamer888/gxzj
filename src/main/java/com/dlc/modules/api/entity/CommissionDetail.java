package com.dlc.modules.api.entity;

import java.io.Serializable;
import java.util.Date;

public class CommissionDetail implements Serializable {
    private Long id;

    private Long agentId;

    private Integer money;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public CommissionDetail(Long id, Long agentId, Integer money, Date createTime) {
        this.id = id;
        this.agentId = agentId;
        this.money = money;
        this.createTime = createTime;
    }

    public CommissionDetail() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
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
        sb.append(", agentId=").append(agentId);
        sb.append(", money=").append(money);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}