package com.dlc.modules.api.entity;

import java.io.Serializable;
import java.util.Date;

public class UserConsume implements Serializable {
    private Long id;

    private Long deviceId;

    private String deviceNo;

    private Integer totalPrice;

    private Integer num;

    private Integer price;

    //状态（0未支付1已完成 2未出货 3已退款）
    private int status;

    private int type;

    private Date createTime;

    private String transactionNo;

    private String orderNo;

    private String goodsName;

    private String goodsImgUrl;

    private String openId;

    private String imei;

    private static final long serialVersionUID = 1L;

    /*public UserConsume(Long id, Long deviceId, String deviceNo, Integer totalPrice, Integer num, Integer price, Byte status, Byte type, Date createTime, String transactionNo, String orderNo, String goodsName, String goodsImgUrl) {
        this.id = id;
        this.deviceId = deviceId;
        this.deviceNo = deviceNo;
        this.totalPrice = totalPrice;
        this.num = num;
        this.price = price;
        this.status = status;
        this.type = type;
        this.createTime = createTime;
        this.transactionNo = transactionNo;
        this.orderNo = orderNo;
        this.goodsName = goodsName;
        this.goodsImgUrl = goodsImgUrl;
    }
*/
    public UserConsume() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo == null ? null : deviceNo.trim();
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo == null ? null : transactionNo.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public String getGoodsImgUrl() {
        return goodsImgUrl;
    }

    public void setGoodsImgUrl(String goodsImgUrl) {
        this.goodsImgUrl = goodsImgUrl == null ? null : goodsImgUrl.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", deviceId=").append(deviceId);
        sb.append(", deviceNo=").append(deviceNo);
        sb.append(", totalPrice=").append(totalPrice);
        sb.append(", num=").append(num);
        sb.append(", price=").append(price);
        sb.append(", status=").append(status);
        sb.append(", type=").append(type);
        sb.append(", createTime=").append(createTime);
        sb.append(", transactionNo=").append(transactionNo);
        sb.append(", orderNo=").append(orderNo);
        sb.append(", goodsName=").append(goodsName);
        sb.append(", goodsImgUrl=").append(goodsImgUrl);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}