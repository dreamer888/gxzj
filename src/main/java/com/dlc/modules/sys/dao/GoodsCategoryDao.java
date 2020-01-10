package com.dlc.modules.sys.dao;

import com.dlc.modules.sys.entity.GoodsCategoryEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 商品分类
 * 
 * @author dlc.dg.java
 *
 */
@Mapper
public interface GoodsCategoryDao extends BaseDao<GoodsCategoryEntity> {

    List<Map<String,Object>> queryByCondition(Map<String, Object> map);
}
