package com.dlc.modules.api.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyOrder implements Serializable {
    private Long id;

    private Long producer;

    private Long customer;

    private String orderNo;

    private Long addrId;

    private Integer orderNum;

    private String transactionNo;

    private Integer goodsSum;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    private Integer realPayment;

    private String logisticsNo;

    private String logisticsName;

    private String company;//物流公司编码（不支持中文）

    private Byte status;

    private Date payTime;

    private Date deliveryTime;

    private Date tradeTime;

    private Date createTime;

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    private Long userId;//用户id

    private String imgUrl;//商品主图片

    private String name;//商品名称

    private Integer price;//商品价格

    private String catName;//商品分类名称

    private Integer goodsId;//商品Id

    private String transactionNumber;//流水号

    private Integer money;//钱包明细表里面的money

    private String openId;


    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public List<OrderDetail> setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
        return orderDetailList;
    }

    private List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();


    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getImgUrl() {

        return imgUrl;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getCatName() {
        return catName;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    private static final long serialVersionUID = 1L;

    public MyOrder(Long id, Long producer, Long customer, String orderNo, Long addrId, Integer orderNum, String transactionNo, Integer goodsSum, Integer realPayment, String logisticsNo, String logisticsName, Byte status, Date payTime, Date deliveryTime, Date tradeTime, Date createTime,Long userId) {
        this.id = id;
        this.producer = producer;
        this.customer = customer;
        this.orderNo = orderNo;
        this.addrId = addrId;
        this.orderNum = orderNum;
        this.transactionNo = transactionNo;
        this.goodsSum = goodsSum;
        this.realPayment = realPayment;
        this.logisticsNo = logisticsNo;
        this.logisticsName = logisticsName;
        this.status = status;
        this.payTime = payTime;
        this.deliveryTime = deliveryTime;
        this.tradeTime = tradeTime;
        this.createTime = createTime;
        this.userId = userId;
    }

    public MyOrder() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProducer() {
        return producer;
    }

    public void setProducer(Long producer) {
        this.producer = producer;
    }

    public Long getCustomer() {
        return customer;
    }

    public void setCustomer(Long customer) {
        this.customer = customer;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Long getAddrId() {
        return addrId;
    }

    public void setAddrId(Long addrId) {
        this.addrId = addrId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo == null ? null : transactionNo.trim();
    }

    public Integer getGoodsSum() {
        return goodsSum;
    }

    public void setGoodsSum(Integer goodsSum) {
        this.goodsSum = goodsSum;
    }

    public Integer getRealPayment() {
        return realPayment;
    }

    public void setRealPayment(Integer realPayment) {
        this.realPayment = realPayment;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo == null ? null : logisticsNo.trim();
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName == null ? null : logisticsName.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Date getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    @Override
    public String toString() {
        return "MyOrder{" +
                "id=" + id +
                ", producer=" + producer +
                ", customer=" + customer +
                ", orderNo='" + orderNo + '\'' +
                ", addrId=" + addrId +
                ", orderNum=" + orderNum +
                ", transactionNo='" + transactionNo + '\'' +
                ", goodsSum=" + goodsSum +
                ", realPayment=" + realPayment +
                ", logisticsNo='" + logisticsNo + '\'' +
                ", logisticsName='" + logisticsName + '\'' +
                ", status=" + status +
                ", payTime=" + payTime +
                ", deliveryTime=" + deliveryTime +
                ", tradeTime=" + tradeTime +
                ", createTime=" + createTime +
                ", userId=" + userId +
                ", imgUrl='" + imgUrl + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", catName='" + catName + '\'' +
                '}';
    }
}