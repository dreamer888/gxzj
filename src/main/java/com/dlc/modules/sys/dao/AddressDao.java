package com.dlc.modules.sys.dao;


import com.dlc.modules.sys.entity.AddressEntity;
import com.dlc.modules.sys.entity.AgentEntity;

import java.util.List;
import java.util.Map;

public interface AddressDao extends BaseDao<AddressEntity>{
    List<Map<String,Object>> select(Map<String, Object> map);
    AddressEntity queryObject(Long addressId);
}