package com.dlc.modules.sys.service;

import com.dlc.modules.sys.entity.DeviceConsumeTotalEntity;

import java.util.List;
import java.util.Map;

public interface DeviceConsumeTotalService {

    DeviceConsumeTotalEntity queryObject(Long Id);

    List<DeviceConsumeTotalEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    int queryCountPrice();
}
