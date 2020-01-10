package com.dlc.modules.sys.controller;

import com.dlc.common.utils.PageUtils;
import com.dlc.common.utils.Query;
import com.dlc.common.utils.R;
import com.dlc.modules.sys.service.GlWalletService;
import com.dlc.modules.sys.service.IncomeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;



@RestController("sysIncomeController")
@RequestMapping("/sys/income")
public class IncomeController {
	@Autowired
	private IncomeService incomeService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:income:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<Map<String,Object>> incomeList = incomeService.queryList(query);
		int total = incomeService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(incomeList, total, query.getLimit(), query.getPage());
		
		return R.reOk().put("page", pageUtil);
	}



    }
	

