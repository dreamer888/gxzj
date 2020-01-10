package com.dlc.modules.api.entity;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.dlc.common.utils.JacksonHelper ;
/**
 * 廖修坤
 * 快递表
 * */
public class Result {

	private String message = "";//监控状态相关消息，如:3天查询无记录，60天无变化
	@JsonIgnore
	private String nu = "";//单号
	@JsonIgnore
	private String ischeck = "0";//是否签收标记，明细状态请参考state字段
	@JsonIgnore
	private String com = "";//快递公司编码,一律用小写字母，见章五《快递公司编码》
	private String status = "0";//监控状态:polling:监控中，shutdown:结束，abort:中止
	@JsonIgnore
	private ArrayList<ResultItem> data = new ArrayList<ResultItem>();
	@JsonIgnore
	private String state = "0";//快递单当前签收状态，包括0在途中、1已揽收、2疑难、3已签收、4退签、5同城派送中、6退回、7转单等7个状态
	@JsonIgnore
	private String condition = "";//快递单明细状态标记，暂未实现，请忽略

	@SuppressWarnings("unchecked")
	public Result clone() {
		Result r = new Result();
		r.setCom(this.getCom());
		r.setIscheck(this.getIscheck());
		r.setMessage(this.getMessage());
		r.setNu(this.getNu());
		r.setState(this.getState());
		r.setStatus(this.getStatus());
		r.setCondition(this.getCondition());
		r.setData((ArrayList<ResultItem>) this.getData().clone());

		return r;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNu() {
		return nu;
	}

	public void setNu(String nu) {
		this.nu = nu;
	}

	public String getCom() {
		return com;
	}

	public void setCom(String com) {
		this.com = com;
	}

	public ArrayList<ResultItem> getData() {
		return data;
	}

	public void setData(ArrayList<ResultItem> data) {
		this.data = data;
	}

	public String getIscheck() {
		return ischeck;
	}

	public void setIscheck(String ischeck) {
		this.ischeck = ischeck;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	@Override
	public String toString() {
		return JacksonHelper.toJSON(this);
	}
}
