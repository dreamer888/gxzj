package com.dlc.modules.sys.service.impl;

import com.dlc.modules.sys.dao.AdvertisingDao;
import com.dlc.modules.sys.entity.AdvertisingEntity;
import com.dlc.modules.sys.service.AdvertisingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("advertisingService")
public class AdvertisingServiceImpl implements AdvertisingService {

    @Autowired
    private AdvertisingDao advertisingDao;

    @Override
    public AdvertisingEntity queryObject(Long advertisingId) {
        return advertisingDao.queryObject(advertisingId);
    }

    @Override
    public List<AdvertisingEntity> queryList(Map<String, Object> map) {
        return advertisingDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return advertisingDao.queryTotal(map);
    }

    @Override
    public void save(AdvertisingEntity advertising) {
        advertisingDao.save(advertising);
    }

    @Override
    public void update(AdvertisingEntity advertising) {
        advertisingDao.update(advertising);
    }

    @Override
    public void updates(AdvertisingEntity advertising) {
        advertisingDao.updates(advertising);
    }

    @Override
    public void delete(Long advertisingId) {
        advertisingDao.delete(advertisingId);
    }

    @Override
    public void deleteBatch(Long[] advertisingIds) {
        advertisingDao.deleteBatch(advertisingIds);
    }
}
