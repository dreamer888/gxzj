package com.dlc.modules.sys.service.impl;

import com.dlc.modules.sys.dao.GoodsCategoryDao;
import com.dlc.modules.sys.entity.GoodsCategoryEntity;
import com.dlc.modules.sys.service.GoodsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("goodsCategoryService")
public class GoodsCategoryServiceImpl implements GoodsCategoryService {

	@Autowired
	private GoodsCategoryDao goodsCategoryDao;
	
	@Override
	public GoodsCategoryEntity queryObject(int id){
		return goodsCategoryDao.queryObject(id);
	}
	
	@Override
	public List<GoodsCategoryEntity> queryList(Map<String, Object> map){
		return goodsCategoryDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return goodsCategoryDao.queryTotal(map);
	}
	
	@Override
	public void save(GoodsCategoryEntity goodsCategory){
		goodsCategoryDao.save(goodsCategory);
	}
	
	@Override
	public void update(GoodsCategoryEntity goodsCategory){
		goodsCategoryDao.update(goodsCategory);
	}
	
	@Override
	public void delete(int id){
		goodsCategoryDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Object[] ids){
		goodsCategoryDao.deleteBatch(ids);
	}

	@Override
	public List<Map<String, Object>> queryByCondition(Map<String, Object> map) {
		return goodsCategoryDao.queryByCondition(map);
	}


}
