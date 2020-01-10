package com.dlc.modules.sys.service;

import com.dlc.modules.sys.entity.AgencyEntity;

import java.util.List;
import java.util.Map;

public interface AgencyCommService {

    AgencyEntity queryObject(Long agencycommId);

    List<AgencyEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(AgencyEntity agencycomm);

    void update(AgencyEntity agencycomm);

    void delete(Long agencycommId);

    void deleteBatch(Long[] agencycommIds);



}
