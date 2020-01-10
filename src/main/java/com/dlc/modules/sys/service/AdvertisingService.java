package com.dlc.modules.sys.service;

import com.dlc.modules.sys.entity.AdvertisingEntity;

import java.util.List;
import java.util.Map;

public interface AdvertisingService {

    AdvertisingEntity queryObject(Long advertisingId);

    List<AdvertisingEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(AdvertisingEntity advertising);

    void update(AdvertisingEntity advertising);

    void updates(AdvertisingEntity advertising);

    void delete(Long advertisingId);

    void deleteBatch(Long[] advertisingIds);
}
