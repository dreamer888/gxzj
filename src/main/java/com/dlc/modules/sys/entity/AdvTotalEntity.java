package com.dlc.modules.sys.entity;

import java.util.Date;

public class AdvTotalEntity {
    //ID
    private Long id;
    //广告ID
    private Long advId;
    //点击量
    private int clickNum;
    //展示量
    private int showNum;
    //创建时间
    private Date createTime;
    //广告类型1.cpc2.com
    private int advType;
    //广告价格
    private int price;
    //统计
    private int goodsPrice;

    //广告主Id
    private Long aId;
    //广告主
    private String aName;
    //广告商id
    private Long dId;
    //广告投放时间
    private Date startTime;
    //广告结束时间
    private Date endTime;
    //广告名称
    private String name;

    private int type;

    private int inCome;

    public int getInCome() {
        return inCome;
    }

    public void setInCome(int inCome) {
        this.inCome = inCome;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    //汇总统计
    private double countPrice;

    public double getCountPrice() {
        return countPrice;
    }

    public void setCountPrice(double countPrice) {
        this.countPrice = countPrice;
    }

    public Long getaId() {
        return aId;
    }

    public void setaId(Long aId) {
        this.aId = aId;
    }

    public Long getdId() {
        return dId;
    }

    public void setdId(Long dId) {
        this.dId = dId;
    }

    public String getaName() {
        return aName;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Long getAdvId() {
        return advId;
    }

    public int getClickNum() {
        return clickNum;
    }

    public int getShowNum() {
        return showNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public int getAdvType() {
        return advType;
    }

    public int getPrice() {
        return price;
    }

    public int getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(int goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAdvId(Long advId) {
        this.advId = advId;
    }

    public void setClickNum(int clickNum) {
        this.clickNum = clickNum;
    }

    public void setShowNum(int showNum) {
        this.showNum = showNum;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setAdvType(int advType) {
        this.advType = advType;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
