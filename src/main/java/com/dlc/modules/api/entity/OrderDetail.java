package com.dlc.modules.api.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class OrderDetail implements Serializable {
    private Long id;

    private String orderNo;

    private Long goodsId;

    private String imgUrl;

    private Integer goodsNum;

    private String goodsName;

    private String cateName;

    private Integer freight;

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    private Integer goodsPrice;

    private Date createTime;

    private Integer categoryId;

    public Integer getFreight() {
        return freight;
    }

    public void setFreight(Integer freight) {
        this.freight = freight;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getCategoryId() {

        return categoryId;
    }

    private static final long serialVersionUID = 1L;

    public OrderDetail(Long id, String orderNo, Long goodsId, String imgUrl, Integer goodsNum, String goodsName, Integer goodsPrice, Date createTime) {
        this.id = id;
        this.orderNo = orderNo;
        this.goodsId = goodsId;
        this.imgUrl = imgUrl;
        this.goodsNum = goodsNum;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.createTime = createTime;
    }

    public OrderDetail() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo(String orderNo) {
        return this.orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public Integer getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Integer goodsPrice) {
        this.goodsPrice = goodsPrice;
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
        sb.append(", orderNo=").append(orderNo);
        sb.append(", goodsId=").append(goodsId);
        sb.append(", imgUrl=").append(imgUrl);
        sb.append(", goodsNum=").append(goodsNum);
        sb.append(", goodsName=").append(goodsName);
        sb.append(", goodsPrice=").append(goodsPrice);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}