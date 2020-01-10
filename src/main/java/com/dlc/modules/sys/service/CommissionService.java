package com.dlc.modules.sys.service;

import com.dlc.modules.sys.entity.CommissionEntity;

import java.util.List;
import java.util.Map;

public interface CommissionService {

    CommissionEntity queryObject(Long Id);

    List<CommissionEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);
}
