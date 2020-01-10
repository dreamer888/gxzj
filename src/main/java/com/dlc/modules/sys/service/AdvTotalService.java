package com.dlc.modules.sys.service;

import com.dlc.modules.sys.entity.AdvTotalEntity;

import java.util.List;
import java.util.Map;

public interface AdvTotalService {

    AdvTotalEntity queryObject(Long Id);

    List<AdvTotalEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    int queryCountPrice();
}
