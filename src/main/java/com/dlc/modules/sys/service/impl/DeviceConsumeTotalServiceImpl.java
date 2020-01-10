package com.dlc.modules.sys.service.impl;

import com.dlc.modules.sys.dao.DeviceConsumeTotalDao;
import com.dlc.modules.sys.entity.DeviceConsumeTotalEntity;
import com.dlc.modules.sys.service.DeviceConsumeTotalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("deviceConsumeTotalService")
public class DeviceConsumeTotalServiceImpl implements DeviceConsumeTotalService {

    @Autowired
    private DeviceConsumeTotalDao deviceConsumeTotalDao;

    @Override
    public DeviceConsumeTotalEntity queryObject(Long Id) {
        return deviceConsumeTotalDao.queryObject(Id);
    }

    @Override
    public List<DeviceConsumeTotalEntity> queryList(Map<String, Object> map) {
        return deviceConsumeTotalDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return deviceConsumeTotalDao.queryTotal(map);
    }

    @Override
    public int queryCountPrice() {
        return deviceConsumeTotalDao.queryCountPrice();
    }

}
