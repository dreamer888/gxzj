package com.dlc.modules.sys.dao;

import com.dlc.modules.sys.entity.DeviceConsumeTotalEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeviceConsumeTotalDao extends BaseDao<DeviceConsumeTotalEntity> {

    int queryCountPrice();
}
