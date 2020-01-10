package com.dlc.modules.sys.controller;

import com.dlc.common.utils.PageUtils;
import com.dlc.common.utils.Query;
import com.dlc.common.utils.R;
import com.dlc.modules.sys.service.DevicelogService;
import com.dlc.modules.sys.service.UserLikeGzhService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController("sysDevicelogController")
@RequestMapping("/sys/devicelog")
public class DevicelogController {
	@Autowired
	private DevicelogService devicelogService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:devicelog:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<Map<String,Object>> devicelogList = devicelogService.queryList(query);
		int total = devicelogService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(devicelogList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	


    }
	

