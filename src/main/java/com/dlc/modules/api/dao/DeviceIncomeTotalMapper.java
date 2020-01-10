package com.dlc.modules.api.dao;

import com.dlc.modules.api.entity.AgentDeviceRelation;
import com.dlc.modules.api.entity.DeviceIncomeTotal;

import java.util.List;
import java.util.Map;

public interface DeviceIncomeTotalMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DeviceIncomeTotal record);

    int insertSelective(DeviceIncomeTotal record);

    DeviceIncomeTotal selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DeviceIncomeTotal record);

    int updateByPrimaryKey(DeviceIncomeTotal record);

    List<Map<String,Object>> query(Map<String,Object> params);

    int queryCount(Map<String,Object> params);

    Map<String,Object> queryTotal(Map<String, Object> params);

    DeviceIncomeTotal queryObjectToday(DeviceIncomeTotal deviceIncomeTotal);

    void updateTotal(AgentDeviceRelation agentDeviceRelation);
}