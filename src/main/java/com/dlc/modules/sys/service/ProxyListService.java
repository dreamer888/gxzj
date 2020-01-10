package com.dlc.modules.sys.service;

import com.dlc.modules.sys.entity.AgencyEntity;

import java.util.List;
import java.util.Map;

public interface ProxyListService {

    AgencyEntity queryObject(Long agencyId);

    List<AgencyEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(AgencyEntity agency);

    void update(AgencyEntity agency);

    void delete(Long agencyId);

    void deleteBatch(Long[] agencyIds);

    /**
     * 查询所有的父代和子代
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
