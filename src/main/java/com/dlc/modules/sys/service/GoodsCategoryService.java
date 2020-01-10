package com.dlc.modules.sys.service;

import com.dlc.modules.sys.entity.GoodsCategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品分类
 * 
 * @author dlc.dg.java
 *
 */
public interface GoodsCategoryService {
	
	GoodsCategoryEntity queryObject(int id);
	
	List<GoodsCategoryEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(GoodsCategoryEntity goodsCategory);
	
	void update(GoodsCategoryEntity goodsCategory);
	
	void delete(int id);
	
	void deleteBatch(Object[] ids);

	List<Map<String,Object>> queryByCondition(Map<String, Object> map);

}
