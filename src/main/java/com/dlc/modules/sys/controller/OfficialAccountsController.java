package com.dlc.modules.sys.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dlc.modules.sys.entity.OfficialAccountsEntity;
import com.dlc.modules.sys.service.OfficialAccountsService;
import com.dlc.common.utils.PageUtils;
import com.dlc.common.utils.Query;
import com.dlc.common.utils.R;




/**
 * 公众号管理表
 * 
 * @author dlc.dg.java
 * @email dlc.dg.java@163.com
 * @date 2018-07-23 11:23:22
 */
@RestController("sysOfficialaccounts")
@RequestMapping("/sys/officialaccounts")
public class OfficialAccountsController {
	@Autowired
	private OfficialAccountsService officialAccountsService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:officialaccounts:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<OfficialAccountsEntity> officialAccountsList = officialAccountsService.queryList(query);
		int total = officialAccountsService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(officialAccountsList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sys:officialaccounts:info")
	public R info(@PathVariable("id") Long id){
		OfficialAccountsEntity officialAccounts = officialAccountsService.queryObject(id);
		
		return R.ok().put("officialAccounts", officialAccounts);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:officialaccounts:save")
	public R save(@RequestBody OfficialAccountsEntity officialAccounts){
		officialAccountsService.save(officialAccounts);
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sys:officialaccounts:update")
	public R update(@RequestBody OfficialAccountsEntity officialAccounts){
		officialAccountsService.update(officialAccounts);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:officialaccounts:delete")
	public R delete(@RequestBody Long[] ids){
		officialAccountsService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
