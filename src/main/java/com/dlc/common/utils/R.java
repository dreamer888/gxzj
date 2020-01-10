package com.dlc.common.utils;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月27日 下午9:59:27
 */
public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	
	public R() {
		put("code", 0);
	}
	
	public static R error() {
		return error(-99, "未知异常，请联系管理员");
	}
	
	public static R error(String msg) {
		return error(-99, msg);
	}

	/** 用于前端返回. */
	public static R reError(CodeAndMsg codeAndMsg, Object data) {
		R r = new R();
		r.put("code", codeAndMsg.getCode());
		r.put("msg", codeAndMsg.getMsg());
		r.put("data",data);
		if (data == null )
			r.remove("data");
		return r;
	}
	/** 用于前端返回. */
	public static R reError(CodeAndMsg codeAndMsg) {
		R r = new R();
		r.put("code", codeAndMsg.getCode());
		r.put("msg", codeAndMsg.getMsg());
		return r;
	}
	/** 用于前端返回. */
	public static R reError(String msg) {
		R r = new R();
		r.put("code", 0);
		r.put("msg", msg);
		return r;
	}
	/** 用于前端返回. */
	public static R reOk(Object data) {
		R r = new R();
		r.put("code", 1);
		r.put("msg", "成功");
		r.put("data",data);
//		if (data == null )
//			r.remove("data");
		return r;
	}
    /** 用于前端返回. */
//    public static R reOkYingshi(Object data) {
//        R r = new R();
//        r.put("code", 1);
//        r.put("msg", "成功");
//        r.put("data",data);
//        return r;
//    }
	/** 用于前端返回. */
	public static R reOk() {
		R r = new R();
		r.put("code", 1);
		r.put("msg", "成功");
		return r;
	}

	/** 用于前端返回. */
	public static R reR(String str) {
		R r = new R();
		r.put("code", 1);
		r.put("msg", str);
		return r;
	}

	public static R error(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static R ok(String msg) {
		R r = new R();
		r.put("msg", msg);
		return r;
	}
	
	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
		return r;
	}
	
	public static R ok() {
		return new R();
	}

	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
