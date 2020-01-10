package com.dlc.modules.sys.service.impl;

import com.dlc.modules.sys.dao.ConductDao;
import com.dlc.modules.sys.dao.SupplierDao;
import com.dlc.modules.sys.entity.AgencyEntity;
import com.dlc.modules.sys.service.ConductService;
import com.dlc.modules.sys.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("conductService")
public class ConductServiceImpl implements ConductService {

    @Autowired
    private ConductDao conductDao;

    @Override
    public AgencyEntity queryObject(Long agencyId) {
        return conductDao.queryObject(agencyId);
    }

    @Override
    public List<AgencyEntity> queryList(Map<String, Object> map) {
        return conductDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return conductDao.queryTotal(map);
    }

    @Override
    public void save(AgencyEntity agency) {
        conductDao.save(agency);
    }

    @Override
    public void update(AgencyEntity agency) {
        conductDao.update(agency);
    }

    @Override
    public void delete(Long agencyId) {
        conductDao.delete(agencyId);
    }

    @Override
    public void deleteBatch(Long[] agencyIds) {
        conductDao.deleteBatch(agencyIds);
    }

    @Override
    public List<Map<String, Object>> queryByCondition(Map<String, Object> map) {
        return conductDao.queryByCondition(map);
    }

}
