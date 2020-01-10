package com.dlc.modules.api.dao;

import com.dlc.modules.api.entity.AgentDeviceRelation;
import com.dlc.modules.api.entity.DeviceConsumeTotal;

import java.util.List;
import java.util.Map;

public interface DeviceConsumeTotalMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DeviceConsumeTotal record);

    int insertSelective(DeviceConsumeTotal record);

    DeviceConsumeTotal selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DeviceConsumeTotal record);

    int updateByPrimaryKey(DeviceConsumeTotal record);

    List<Map<String,Object>> query(Map<String,Object> params);

    int queryCount(Map<String,Object> params);

    Map<String,Object>queryTotal(Map<String, Object> params);

    DeviceConsumeTotal queryObjectToday(DeviceConsumeTotal deviceConsumeTotal);

    int queryObjectTodayCount(Map<String, Object> map);

    void updateTotal(AgentDeviceRelation agentDeviceRelation);

}