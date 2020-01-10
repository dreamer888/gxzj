package com.dlc.modules.api.service.impl;

import com.dlc.modules.api.dao.DeviceIncomeTotalMapper;
import com.dlc.modules.api.service.DeviceIncomeTotalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/***********************************
 *Class by 王楚荣
 *2018/7/18/018
 * **********************************/
@Service
public class DeviceIncomeTotalServiceImpl implements DeviceIncomeTotalService {
    @Autowired
    private DeviceIncomeTotalMapper deviceIncomeTotalMapper;

    @Override
    public List<Map<String, Object>> queryList(Map<String, Object> params) {
        return deviceIncomeTotalMapper.query(params);
    }

    @Override
    public Map<String, Object> queryTotal(Map<String, Object> params) {
        return deviceIncomeTotalMapper.queryTotal(params);
    }

    @Override
    public int queryCount(Map<String, Object> params) {
        return deviceIncomeTotalMapper.queryCount(params);
    }
}
