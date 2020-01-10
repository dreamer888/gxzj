package com.dlc.modules.api.dao;

import com.dlc.modules.api.entity.Devicelog;
import java.util.List;

import java.util.Map;

public interface DevicelogMapper {
    int deleteByPrimaryKey(Long logId);

    int insert(Devicelog record);

    int insertSelective(Devicelog record);

    Devicelog selectByPrimaryKey(Long logId);

    int updateByPrimaryKeySelective(Devicelog record);

    int updateByPrimaryKey(Devicelog record);

    List<Map<String,Object>> queryList(Map<String,Object> params);
}