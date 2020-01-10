package com.dlc.modules.api.service.impl;

import com.dlc.modules.api.dao.DeviceConsumeTotalMapper;
import com.dlc.modules.api.service.DeviceConsumeTotalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/***********************************
 *Class by 王楚荣
 *2018/7/17/017
 * **********************************/
@Service
public class DeviceConsumeTotalServiceImpl implements DeviceConsumeTotalService {
    @Autowired
    private DeviceConsumeTotalMapper deviceConsumeTotalMapper;

    @Override
    public List<Map<String, Object>> queryList(Map<String, Object> params) {
        return deviceConsumeTotalMapper.query(params);
    }

    @Override
    public Map<String, Object> queryTotal(Map<String, Object> params) {
        return deviceConsumeTotalMapper.queryTotal(params);
    }

    @Override
    public int queryCount(Map<String, Object> params) {
        return deviceConsumeTotalMapper.queryCount(params);
    }
}
