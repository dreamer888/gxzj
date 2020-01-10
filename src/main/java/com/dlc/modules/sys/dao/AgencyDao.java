package com.dlc.modules.sys.dao;

import com.dlc.modules.sys.entity.AgencyEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AgencyDao extends BaseDao<AgencyEntity> {

    List<Map<String,Object>> queryByCondition(Map<String, Object> map);


}
