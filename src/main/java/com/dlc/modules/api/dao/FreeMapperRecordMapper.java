package com.dlc.modules.api.dao;

import com.dlc.modules.api.entity.FreeMapperRecord;

import java.util.Map;

public interface FreeMapperRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FreeMapperRecord record);

    int insertSelective(FreeMapperRecord record);

    FreeMapperRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FreeMapperRecord record);

    int updateByPrimaryKey(FreeMapperRecord record);

    FreeMapperRecord queryRecordByAppId(Map<String, Object> map);
}