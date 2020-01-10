package com.dlc.modules.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class Goods implements Serializable {
    private Long id;

    private Long userId;

    private Integer categoryId;

    private String name;

    private Integer price;

    private String imgUrl;

    private Byte status;

    private Integer freight;

    private String remark;

    private String carouselImgUrl;

    private String remarkImgUrl;

    private Byte deleteStatus;

    private Integer hitCount=0;//点击数：用户记录用户购买单个商品数量

    private String cateName;//分类名

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public Integer getHitCount() {
        return hitCount;
    }

    public void setHitCount(Integer hitCount) {
        this.hitCount = hitCount;
    }

    private Integer monthlySales;//月销量

    public Integer getMonthlySales() {
        return monthlySales;
    }

    public void setMonthlySales(Integer monthlySales) {
        this.monthlySales = monthlySales;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Goods(Long id, Long userId, Integer categoryId, String name, Integer price, String imgUrl, Byte status, Integer freight, String remark, String carouselImgUrl, String remarkImgUrl, Byte deleteStatus, Date createTime) {
        this.id = id;
        this.userId = userId;
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
        this.status = status;
        this.freight = freight;
        this.remark = remark;
        this.carouselImgUrl = carouselImgUrl;
        this.remarkImgUrl = remarkImgUrl;
        this.deleteStatus = deleteStatus;
        this.createTime = createTime;
    }

    public Goods() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getFreight() {
        return freight;
    }

    public void setFreight(Integer freight) {
        this.freight = freight;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCarouselImgUrl() {
        return carouselImgUrl;
    }

    public void setCarouselImgUrl(String carouselImgUrl) {
        this.carouselImgUrl = carouselImgUrl == null ? null : carouselImgUrl.trim();
    }

    public String getRemarkImgUrl() {
        return remarkImgUrl;
    }

    public void setRemarkImgUrl(String remarkImgUrl) {
        this.remarkImgUrl = remarkImgUrl == null ? null : remarkImgUrl.trim();
    }

    public Byte getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Byte deleteStatus) {
        this.deleteStatus = deleteStatus;
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
        sb.append(", userId=").append(userId);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", name=").append(name);
        sb.append(", price=").append(price);
        sb.append(", imgUrl=").append(imgUrl);
        sb.append(", status=").append(status);
        sb.append(", freight=").append(freight);
        sb.append(", remark=").append(remark);
        sb.append(", carouselImgUrl=").append(carouselImgUrl);
        sb.append(", remarkImgUrl=").append(remarkImgUrl);
        sb.append(", deleteStatus=").append(deleteStatus);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}