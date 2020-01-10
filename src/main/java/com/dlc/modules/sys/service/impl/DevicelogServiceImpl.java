package com.dlc.modules.sys.service.impl;

import com.dlc.modules.sys.dao.DevicelogDao;
import com.dlc.modules.sys.service.DevicelogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/***********************************
 *Class by 王楚荣
 *2018/7/23/023
 * **********************************/
@Service("devicelogService")
public class DevicelogServiceImpl implements DevicelogService {
    @Autowired
    private DevicelogDao devicelogDao;

    @Override
    public List<Map<String, Object>> queryList(Map<String, Object> map) {
        return devicelogDao.queryListMapByMap(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return devicelogDao.queryTotal(map);
    }
}
