package com.dlc.modules.sys.controller;

import com.dlc.common.utils.PageUtils;
import com.dlc.common.utils.Query;
import com.dlc.common.utils.R;
import com.dlc.modules.api.entity.ExceptionMessage;
import com.dlc.modules.sys.entity.DeviceEntity;
import com.dlc.modules.sys.entity.ExceptionMessageEntity;
import com.dlc.modules.sys.service.ExceptionMessageService;
import com.dlc.modules.sys.service.UserLikeGzhService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController("sysExceptionMessageController")
@RequestMapping("/sys/exceptionMessage")
public class ExceptionMessageController {
	@Autowired
	private ExceptionMessageService exceptionMessageService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:exceptionmessage:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<Map<String,Object>> exceptionMessageList = exceptionMessageService.queryList(query);
		int total = exceptionMessageService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(exceptionMessageList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:exceptionmessage:save")
	public R save(@RequestBody ExceptionMessageEntity exceptionMessage){
		exceptionMessageService.save(exceptionMessage);
		return R.ok();
	}
	


    }
	

