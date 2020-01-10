package com.dlc.modules.sys.controller;

import com.dlc.common.utils.PageUtils;
import com.dlc.common.utils.Query;
import com.dlc.common.utils.R;
import com.dlc.modules.sys.entity.SysConsoleEntity;
import com.dlc.modules.sys.service.SysConsoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-01-08 09:59:06
 */
@RestController
@RequestMapping("/sys/sysconsole")
public class SysConsoleController {
	@Autowired
	private SysConsoleService sysConsoleService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:sysconsole:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<SysConsoleEntity> sysConsoleList = sysConsoleService.queryList(query);
		int total = sysConsoleService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(sysConsoleList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sys:sysconsole:info")
	public R info(@PathVariable("id") Integer id){
		SysConsoleEntity sysConsole = sysConsoleService.queryObject(id);
		
		return R.ok().put("sysConsole", sysConsole);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:sysconsole:save")
	public R save(@RequestBody SysConsoleEntity sysConsole){
		sysConsoleService.save(sysConsole);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sys:sysconsole:update")
	public R update(HttpServletRequest request){

		SysConsoleEntity sysConsole = new SysConsoleEntity();

		Integer id = Integer.valueOf(request.getParameter("id"));
		String title = request.getParameter("title");
		String content = request.getParameter("content");

		sysConsole.setId(id);
		sysConsole.setTitle(title);
		sysConsole.setContent(content);
		
		sysConsoleService.update(sysConsole);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:sysconsole:delete")
	public R delete(@RequestBody Integer[] ids){
		sysConsoleService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
