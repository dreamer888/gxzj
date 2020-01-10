package com.dlc.modules.sys.controller;

import com.dlc.common.utils.R;
import com.dlc.modules.sys.service.AddressService;
import com.dlc.modules.sys.service.AgentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * 纸巾机设备表
 * 
 * @author dlc.dg.java
 * @email dlc.dg.java@163.com
 * @date 2018-07-15 18:06:09
 */
@RestController("sysAddressController")
@RequestMapping("/sys/address")
public class AddressController {
	@Autowired
	private AddressService addressService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/select")
	@RequiresPermissions("sys:address:list")
	public R list(@RequestParam Map<String, Object> params){
		List<Map<String,Object>> list = addressService.select(params);
		return R.ok().put("address", list);
	}
	

	
}
