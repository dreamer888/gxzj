package com.dlc.modules.api.entity;

import java.io.Serializable;
import java.util.Date;

public class AdvTotal implements Serializable {
    private Long id;

    private Long advId;

    private Integer clickNum;

    private Integer showNum;

    private Date createTime;
    //广告类型
    private Byte advType;
    //广告价格
    private Integer price;

    private static final long serialVersionUID = 1L;

    public AdvTotal(Long id, Long advId, Integer clickNum, Integer showNum, Date createTime,Byte advType,Integer price) {
        this.id = id;
        this.advId = advId;
        this.clickNum = clickNum;
        this.showNum = showNum;
        this.createTime = createTime;
        this.advType = advType;
        this.price = price;
    }

    public AdvTotal() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdvId() {
        return advId;
    }

    public void setAdvId(Long advId) {
        this.advId = advId;
    }

    public Integer getClickNum() {
        return clickNum;
    }

    public void setClickNum(Integer clickNum) {
        this.clickNum = clickNum;
    }

    public Integer getShowNum() {
        return showNum;
    }

    public void setShowNum(Integer showNum) {
        this.showNum = showNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Byte getAdvType() {
        return advType;
    }

    public void setAdvType(Byte advType) {
        this.advType = advType;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", advId=").append(advId);
        sb.append(", clickNum=").append(clickNum);
        sb.append(", showNum=").append(showNum);
        sb.append(", createTime=").append(createTime);
        sb.append(", advType=").append(advType);
        sb.append(", price=").append(price);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}