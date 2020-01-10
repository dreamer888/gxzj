package com.dlc.modules.sys.service;

import com.dlc.modules.sys.entity.AgencyEntity;

import java.util.List;
import java.util.Map;

public interface ConductService {

    AgencyEntity queryObject(Long agencyId);

    List<AgencyEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(AgencyEntity agency);

    void update(AgencyEntity agency);

    void delete(Long agencyId);

    void deleteBatch(Long[] agencyIds);

    List<Map<String,Object>> queryByCondition(Map<String, Object> map);

}
