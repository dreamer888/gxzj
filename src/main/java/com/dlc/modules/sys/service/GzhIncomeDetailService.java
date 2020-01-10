package com.dlc.modules.sys.service;

import com.dlc.modules.sys.entity.GzhIncomeDetailEntity;

import java.util.List;
import java.util.Map;

public interface GzhIncomeDetailService {
    GzhIncomeDetailEntity queryObject(Long gzhId);

    List<GzhIncomeDetailEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    int queryCountPrice();
}
