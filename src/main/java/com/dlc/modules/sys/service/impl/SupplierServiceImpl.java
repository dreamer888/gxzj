package com.dlc.modules.sys.service.impl;

import com.dlc.modules.sys.dao.SupplierDao;
import com.dlc.modules.sys.entity.AgencyEntity;
import com.dlc.modules.sys.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("supplierService")
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierDao supplierDao;

    @Override
    public AgencyEntity queryObject(Long agencyId) {
        return supplierDao.queryObject(agencyId);
    }

    @Override
    public List<AgencyEntity> queryList(Map<String, Object> map) {
        return supplierDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return supplierDao.queryTotal(map);
    }

    @Override
    public void save(AgencyEntity agency) {
        supplierDao.save(agency);
    }

    @Override
    public void update(AgencyEntity agency) {
        supplierDao.update(agency);
    }

    @Override
    public void delete(Long agencyId) {
        supplierDao.delete(agencyId);
    }

    @Override
    public void deleteBatch(Long[] agencyIds) {
        supplierDao.deleteBatch(agencyIds);
    }

    @Override
    public List<Map<String, Object>> queryByCondition(Map<String, Object> map) {
        return supplierDao.queryByCondition(map);
    }

}
