package com.dlc.modules.sys.controller;

import com.dlc.common.utils.PageUtils;
import com.dlc.common.utils.Query;
import com.dlc.common.utils.R;
import com.dlc.modules.sys.entity.GoodsCategoryEntity;
import com.dlc.modules.sys.service.GoodsCategoryService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 商品分类
 * 
 * @author dlc.dg.java
 * @email dlc.dg.java@163.com
 * @date 2018-05-28 09:53:11
 */
@RestController
@RequestMapping("/sys/goodscategory")
public class SysGoodsCategoryController {

	@Autowired
	private GoodsCategoryService goodsCategoryService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:goodscategory:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<GoodsCategoryEntity> goodsCategoryList = goodsCategoryService.queryList(query);
		int total = goodsCategoryService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(goodsCategoryList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sys:goodscategory:info")
	public R info(@PathVariable("id") int id){
		GoodsCategoryEntity goodsCategory = goodsCategoryService.queryObject(id);
		
		return R.ok().put("goodsCategory", goodsCategory);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:goodscategory:save")
	public R save(@RequestBody GoodsCategoryEntity goodsCategory){
		Map<String,Object> map = new HashMap<>();
		map.put("name",goodsCategory.getName());

		if (!goodsCategoryService.queryByCondition(map).isEmpty()){
			return R.error("该分类名称已经存在");
		}
		goodsCategory.setCreateTime(new Date());
		goodsCategoryService.save(goodsCategory);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sys:goodscategory:update")
	public R update(@RequestBody GoodsCategoryEntity goodsCategory){
		if(!goodsCategory.getName().equals(goodsCategoryService.queryObject(goodsCategory.getId()).getName())){
			Map<String,Object> map = new HashMap<>();
			map.put("name",goodsCategory.getName());

			if (!goodsCategoryService.queryByCondition(map).isEmpty()){
				return R.error("该分类名称已经存在");
			}
		}
		goodsCategoryService.update(goodsCategory);

		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:goodscategory:delete")
	public R delete(@RequestBody Object[] ids){
		goodsCategoryService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
