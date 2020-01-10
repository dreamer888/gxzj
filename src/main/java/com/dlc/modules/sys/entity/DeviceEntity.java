package com.dlc.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 纸巾机设备表
 * 
 * @author dlc.dg.java
 * @email dlc.dg.java@163.com
 * @date 2018-07-15 18:06:09
 */
public class DeviceEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//设备Id
	private Long deviceId;
	//设备编号
	private String deviceNo;
	//设备IMEI
	private String imei;
	//设备地址详情
	private String addressDetail;
	//设备地址Id
	private Long addressId;
	//代理人Id
	private Long proxyId;
	//设备状态（1 在线 2离线 3故障）
	private Integer status;
	//商品库存
	private Integer inventory;
	//
	private String goodsImgUrl;
	//
	private String goodsName;
	//设备里面商品的单价
	private Integer goodsPrice;
	//单价
	private Integer price;
	//创建时间
	private Date createTime;

	private String name;

	private Long agentId;

	public Long getAgentId() {
		return agentId;
	}

	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 设置：设备Id
	 */
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	/**
	 * 获取：设备Id
	 */
	public Long getDeviceId() {
		return deviceId;
	}
	/**
	 * 设置：设备编号
	 */
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	/**
	 * 获取：设备编号
	 */
	public String getDeviceNo() {
		return deviceNo;
	}
	/**
	 * 设置：设备IMEI
	 */
	public void setImei(String imei) {
		this.imei = imei;
	}
	/**
	 * 获取：设备IMEI
	 */
	public String getImei() {
		return imei;
	}
	/**
	 * 设置：设备地址详情
	 */
	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}
	/**
	 * 获取：设备地址详情
	 */
	public String getAddressDetail() {
		return addressDetail;
	}
	/**
	 * 设置：设备地址Id
	 */
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	/**
	 * 获取：设备地址Id
	 */
	public Long getAddressId() {
		return addressId;
	}
	/**
	 * 设置：代理人Id
	 */
	public void setProxyId(Long proxyId) {
		this.proxyId = proxyId;
	}
	/**
	 * 获取：代理人Id
	 */
	public Long getProxyId() {
		return proxyId;
	}
	/**
	 * 设置：设备状态（1 在线 2离线 3故障）
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：设备状态（1 在线 2离线 3故障）
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：商品库存
	 */
	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}
	/**
	 * 获取：商品库存
	 */
	public Integer getInventory() {
		return inventory;
	}
	/**
	 * 设置：
	 */
	public void setGoodsImgUrl(String goodsImgUrl) {
		this.goodsImgUrl = goodsImgUrl;
	}
	/**
	 * 获取：
	 */
	public String getGoodsImgUrl() {
		return goodsImgUrl;
	}
	/**
	 * 设置：
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	/**
	 * 获取：
	 */
	public String getGoodsName() {
		return goodsName;
	}
	/**
	 * 设置：设备里面商品的单价
	 */
	public void setGoodsPrice(Integer goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	/**
	 * 获取：设备里面商品的单价
	 */
	public Integer getGoodsPrice() {
		return goodsPrice;
	}
	/**
	 * 设置：单价
	 */
	public void setPrice(Integer price) {
		this.price = price;
	}
	/**
	 * 获取：单价
	 */
	public Integer getPrice() {
		return price;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
}
