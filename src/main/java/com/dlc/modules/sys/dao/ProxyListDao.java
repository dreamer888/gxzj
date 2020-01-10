package com.dlc.modules.sys.dao;

import com.dlc.modules.sys.entity.AgencyEntity;
import com.dlc.modules.sys.entity.SysMenuEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProxyListDao extends BaseDao<AgencyEntity> {

    /**
     * 查询所有的父代记子代
     * @param map
     * @return
     */
    List<Map<String,Object>> queryByConditionProxy(Map<String, Object> map);

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     */
    List<AgencyEntity> queryListParentId(Long parentId);


}
