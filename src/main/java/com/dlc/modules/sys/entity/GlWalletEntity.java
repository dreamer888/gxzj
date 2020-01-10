package com.dlc.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;

public class GlWalletEntity implements Serializable {
    private Long id;

    private Long balance;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public GlWalletEntity(Long id, Long balance, Date createTime) {
        this.id = id;
        this.balance = balance;
        this.createTime = createTime;
    }

    public GlWalletEntity() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
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
        sb.append(", balance=").append(balance);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}