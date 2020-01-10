package com.dlc.modules.api.service.impl;

import com.dlc.modules.api.dao.FreeMapperRecordMapper;
import com.dlc.modules.api.entity.FreeMapperRecord;
import com.dlc.modules.api.service.FreeMapperRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenyuexin
 * @version 1.0
 * @date 2018-08-04 19:21
 */
@Service
public class FreeMapperRecordServiceImpl implements FreeMapperRecordService {
    @Autowired
    private FreeMapperRecordMapper freeMapperRecordMapper;

    @Override
    public int addRecord(FreeMapperRecord freeMapperRecord) {
        return freeMapperRecordMapper.insertSelective(freeMapperRecord);

    }

    @Override
    public FreeMapperRecord queryRecordByAppId(String appId, String openId) {

        Map<String, Object> map = new HashMap<>();
        map.put("appId", appId);
        map.put("openId", openId);
        return freeMapperRecordMapper.queryRecordByAppId(map);
    }
}
