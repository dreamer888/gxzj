package com.dlc.modules.api.dao;

import com.dlc.modules.api.entity.TempUserDevice;
import org.springframework.stereotype.Repository;

import java.util.Map;
@Repository
public interface TempUserDeviceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TempUserDevice record);

    int insertSelective(TempUserDevice record);

    TempUserDevice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TempUserDevice record);

    int updateByPrimaryKey(TempUserDevice record);

    TempUserDevice queryUserDeviceInfo(Map<String, Object> parmMap);

    void deleteRecordByMap(Map<String, Object> parmMap);
}