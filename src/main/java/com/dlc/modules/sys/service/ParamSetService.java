package com.dlc.modules.sys.service;

import com.dlc.modules.api.entity.ParamSet;
import com.dlc.modules.sys.entity.ParamSetEntity;

import java.util.List;
import java.util.Map;

public interface ParamSetService {
    ParamSetEntity queryObject(int paramId);

    List<ParamSetEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(ParamSetEntity param);

    void update(ParamSetEntity param);

    void delete(Long paramId);

    void deleteBatch(Long[] paramIds);

    ParamSetEntity queryParamSetInfo();

    List<Map<String,Object>> queryByConditions(Map<String, Object> map);
}
