package com.dlc.modules.api.entity;
/**
 * 廖修坤
 * 快递详情表
 * */
public class ResultItem {
	String time;//时间，原始格式
	String context;//内容
	String ftime;//格式化后时间
	String nu = "";//单号

	public String getNu() {
		return nu;
	}

	public void setNu(String nu) {
		this.nu = nu;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getFtime() {
		return ftime;
	}

	public void setFtime(String ftime) {
		this.ftime = ftime;
	}

}
